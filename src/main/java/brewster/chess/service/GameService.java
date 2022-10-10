package brewster.chess.service;

import brewster.chess.exception.InvalidMoveException;
import brewster.chess.exception.PieceNotFound;
import brewster.chess.model.Game;
import brewster.chess.model.Player;
import brewster.chess.model.User;
import brewster.chess.model.request.MoveRequest;
import brewster.chess.model.request.PromotionRequest;
import brewster.chess.model.response.GameResponse;
import brewster.chess.model.response.NewGameResponse;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.PieceFactory;
import brewster.chess.repository.GameRepository;
import brewster.chess.repository.UserRepository;
import brewster.chess.service.model.GamePiecesDto;
import org.springframework.stereotype.Service;

import brewster.chess.model.piece.Spot;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GameService {
    private final GameRepository repository;
    private final CheckService checkService;
    private final UserRepository userRepository;

    public GameService(GameRepository repository, CheckService checkService, UserRepository userRepository) {
        this.repository = repository;
        this.checkService = checkService;
        this.userRepository = userRepository;
    }

    public NewGameResponse startGame(User user1, User user2) {
        Game newGame = repository.save(new Game(user1, user2));

        return new NewGameResponse(newGame.getId(), newGame.getPlayer1(), newGame.getPlayer2());
    }

    public List<Spot> getLegalMoves(Game game, int position) {
        return findPiece(game, position)
                .calculateLegalMoves(getAllSpots(game), getFoesPieces(game));
    }

    public GameResponse movePiece(Game game, MoveRequest request) {
        GamePiecesDto dto = getGamePiecesDto(game);
        Piece piece = findPiece(game, request.getStart());
        piece.move(request.getEnd());

        if (checkService.isInCheckAfterMove(dto)) {
            piece.move(request.getStart());
            throw new InvalidMoveException("You are in check");
        }
        Optional<Piece> potentialFoe = findPiece(getFoesPieces(game), request.getEnd());
        potentialFoe.ifPresent(foe -> getFoesPieces(game).remove(foe));

        if (checkService.didCheck(dto)){
            game.setCheck(true);
            if (checkService.didCheckMate(dto)){
                game.setActive(false);
                return getGameOverResponse(game);
            }
        }
//        List<String> moves = repository.findById(game.getId()).map(Game::getMoves).orElseThrow();
        game.setMoves(updateMoveMessage(game, piece.getType().name, request, potentialFoe, game.isCheck()));
        game.setWhitesTurn(!game.isWhitesTurn());
        repository.save(game);
        return getGameResponse(game);
    }

//    public GameResponse restart(Game game) {
//        game.setWhitesTurn(true);
//        game.setCheck(false);
//        game.setMoves("");
//        game.setActive(true);
//    }


    private String updateMoveMessage(Game game, String pieceName, MoveRequest request, Optional<Piece> potentialFoe, boolean isCheck){
        int turn = game.getMoves().split("\\.").length;
        StringBuilder message = new StringBuilder(game.getMoves() + turn + ". " + getCurrentPlayer(game).getName());
        message.append(" has moved his ").append(pieceName).append(" from ").append(request.getStart()).append(" to ").append(request.getEnd());
        potentialFoe.ifPresent(foe -> message.append(" and has captured a ").append(foe.getType()));
        if (isCheck) { message.append(" - CHECK!"); }
        return message.append("\n").toString();
    }

    private GamePiecesDto getGamePiecesDto(Game game){
        return new GamePiecesDto(getAllSpots(game), getCurrentTeam(game), getFoesPieces(game));
    }

    public GameResponse implementPromotion(Game game, PromotionRequest request) {
        List<Piece> pieces = getCurrentTeam(game);
        Piece piece = findPiece(pieces, request.getOldPosition()).orElseThrow(PieceNotFound::new);
        pieces.remove(piece);
        pieces.add(new PieceFactory(piece.getTeam(), request.getNewPosition(), request.getType()).getInstance());
//        pieces.add(new Queen(piece.getTeam(), request.getNewPosition() / 10, request.getNewPosition() % 10));
        return getGameResponse(game);
    }

    public GameResponse requestDraw(Game game) {
        if (!game.isCheck() && checkService.isStaleMate(getGamePiecesDto(game))){
            game.setActive(false);
            //todo update user win totals with a draw. by calling user service?
            return getGameOverResponse(game);
        }
        //todo request other player for Draw
        return null;
    }

    private GameResponse getGameResponse(Game game) {
        //todo
        return new GameResponse(game);
    }

    private GameResponse getGameOverResponse(Game game) {
        User winner = getCurrentPlayer(game).getUser();
        User loser = getOpponent(game).getUser();
        userRepository.save(winner.addWin());
        userRepository.save(loser.addLoss());
        repository.delete(game);
        return new GameResponse(false,  winner.getName(), loser.getName());
    }

    public List<Piece> getAllPieces(Game game){
        return Stream.concat(game.getPlayer1().getPieces().stream(), game.getPlayer2().getPieces().stream()).collect(Collectors.toList());
    }
    public List<Piece> getFoesPieces(Game game){
        return game.isWhitesTurn() ? game.getPlayer2().getPieces() : game.getPlayer1().getPieces();
    }
    public List<Piece> getCurrentTeam(Game game){
        return game.isWhitesTurn() ? game.getPlayer1().getPieces() : game.getPlayer2().getPieces();
    }
    public Player getCurrentPlayer(Game game){
        return game.isWhitesTurn() ? game.getPlayer1() : game.getPlayer2();
    }
    public Player getOpponent(Game game){
        return game.isWhitesTurn() ? game.getPlayer2() : game.getPlayer1();
    }
    public Stream<Piece> getAllPiecesStream(Game game){
        return Stream.concat(game.getPlayer1().getPieces().stream(), game.getPlayer2().getPieces().stream());
    }

    public List<Spot> getAllSpots(Game game){
        return Stream.concat(game.getPlayer1().getPieces().stream(), game.getPlayer2().getPieces().stream())
                .map(Piece::getSpot)
                .collect(Collectors.toList());
    }
//    public List<Spot> convertToSpots(Player player){
//        return player.getPieces().stream().map(Piece::getSpot).collect(Collectors.toList());
//    }
//    public boolean isOccupied(Game game, int location){
//        return getAllPieces(game).anyMatch(piece -> piece.isAtPosition(location));
//    }


    private Piece findPiece(Game game, int position) {
        return getCurrentTeam(game).stream()
                .filter(piece -> piece.isAtPosition(position))
                .findAny().orElseThrow(PieceNotFound::new);
    }

    private Optional<Piece> findPiece(List<Piece> pieces, int position) {
        return pieces.stream()
                .filter(piece -> piece.isAtPosition(position))
                .findAny();
    }

    void removeFoeIfCaptured(Game game, int end) {
        getFoesPieces(game).stream().filter(p -> p.isAtPosition(end)).findAny()
                .ifPresent(foe -> getFoesPieces(game).remove(foe));
    }

    private boolean isAttack(Game game, int end) {
        return getFoesPieces(game).stream().anyMatch(p -> p.isAtPosition(end));
    }
    private Optional<Piece> isFoeCaptured(Game game, int end) {
        return getFoesPieces(game).stream().filter(p -> p.isAtPosition(end)).findAny();
    }

}
