package brewster.chess.service;

import brewster.chess.exception.GameNotFound;
import brewster.chess.exception.InvalidMoveException;
import brewster.chess.exception.PieceNotFound;
import brewster.chess.model.ChessGame;
import brewster.chess.model.Player;
import brewster.chess.model.User;
import brewster.chess.model.piece.Pawn;
import brewster.chess.model.request.MoveRequest;
import brewster.chess.model.request.PromotionRequest;
import brewster.chess.model.response.GameResponse;
import brewster.chess.model.response.NewGameResponse;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.PieceFactory;
import brewster.chess.model.response.PromotionResponse;
import brewster.chess.repository.GameRepository;
import brewster.chess.repository.UserRepository;
import brewster.chess.service.model.GamePiecesDto;
import org.springframework.stereotype.Service;

import brewster.chess.model.piece.Square;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChessGameService {
    private final GameRepository repository;
    private final CheckService checkService;
    private final UserRepository userRepository;
    private final MoveMessageService moveMessageService;

    public ChessGameService(GameRepository repository, CheckService checkService, UserRepository userRepository, MoveMessageService moveMessageService) {
        this.repository = repository;
        this.checkService = checkService;
        this.userRepository = userRepository;
        this.moveMessageService = moveMessageService;
    }

    public NewGameResponse startGame(User user1, User user2) {
        return new NewGameResponse(repository.save(new ChessGame(user1, user2)));
    }

    public Map<Integer, List<Integer>> getAllMoves(long id) {
        ChessGame game = findGame(id);
        Map<Integer, List<Integer>> allMoves = new HashMap<>();
        for (Piece piece : getCurrentPlayer(game).getPieces()) {
            List<Integer> pieceMoves = getLegalMoves(game, piece.getLocation());
            if (!pieceMoves.isEmpty()) {
                allMoves.put(piece.getLocation(), pieceMoves);
            }
        }
        return allMoves;
    }
    public List<Integer> getLegalMoves(ChessGame game, int position) {
        return findPiece(game, position)
            .calculateLegalMoves(getAllSquares(game), getFoesPieces(game))
            .stream()
            .map(Square::convertToInt)
            .collect(Collectors.toList());
    }
    public List<Integer> getLegalMoves(long id, int position) {
        ChessGame game = findGame(id);
        return findPiece(game, position)
                .calculateLegalMoves(getAllSquares(game), getFoesPieces(game))
                .stream()
                .map(Square::convertToInt)
                .collect(Collectors.toList());
    }
    public GameResponse movePiece(long id, MoveRequest request) {
        return movePiece(findGameWithMoves(id), request);
    }

    public GameResponse movePiece(ChessGame game, MoveRequest request) {
        GamePiecesDto dto = getGamePiecesDto(game);
        Piece piece = findPiece(game, request.getStart());
        piece.move(request.getEnd());

        if (checkService.isInCheckAfterMove(dto)) {
            piece.move(request.getStart());
            throw new InvalidMoveException("You are in check");
        }
        Optional<Piece> potentialFoe = findPiece(getFoesPieces(game), request.getEnd());
        potentialFoe.ifPresent(foe -> getFoesPieces(game).remove(foe));

        boolean isPromotion = isPromotion(piece); //todo need to complete per todo.md
        if (isPromotion) {
            return new PromotionResponse(game, request);
        }
        if (checkService.didCheck(dto)){
            game.setCheck(true);
            if (checkService.didCheckMate(dto)){
                game.setActive(false);
                return getGameOverResponse(game);
            }
        }

        moveMessageService.updateMoveMessage(game, piece.getType().name, request, potentialFoe);
        game.setWhitesTurn(!game.isWhitesTurn());
        repository.save(game);
        return getGameResponse(game);
    }

    private boolean isPromotion(Piece piece) {
        if (piece instanceof Pawn) {
            return piece.getSquare().y == 1 || piece.getSquare().y == 8;
        }
        return false;
    }

    private GamePiecesDto getGamePiecesDto(ChessGame game){
        return GamePiecesDto.builder()
            .squares(getAllSquares(game))
            .friends(getCurrentTeam(game))
            .foes(getFoesPieces(game))
            .build();
    }

    public GameResponse implementPromotion(long id, PromotionRequest request) {
        ChessGame game = findGame(id);
        List<Piece> pieces = getCurrentTeam(game);
        Piece piece = findPiece(pieces, request.getOldPosition()).orElseThrow(PieceNotFound::new);
        pieces.remove(piece);
        pieces.add(new PieceFactory(piece.getTeam(), request.getNewPosition(), request.getType()).getInstance());
//        pieces.add(new Queen(piece.getTeam(), request.getNewPosition() / 10, request.getNewPosition() % 10));
        return getGameResponse(game);
    }

    public GameResponse requestDraw(long id) {
        ChessGame game = findGame(id);
        if (!game.isCheck() && checkService.isStaleMate(getGamePiecesDto(game))){
            game.setActive(false);
            //todo update user win totals with a draw. by calling user service?
            return getGameOverResponse(game);
        }
        //todo request other player for Draw
        return null;
    }

    private ChessGame findGame(long id){
        return repository.findById(id).orElseThrow(GameNotFound::new);
    }
    private ChessGame findGameWithMoves(long id){
        return repository.findGameWithMoves(id).orElseThrow(GameNotFound::new);
    }

    private GameResponse getGameResponse(ChessGame game) {
        //todo
        return new GameResponse(game);
    }

    private GameResponse getGameOverResponse(ChessGame game) {
        User winner = getCurrentPlayer(game).getUser();
        User loser = getOpponent(game).getUser();
        userRepository.save(winner.addWin());
        userRepository.save(loser.addLoss());
        repository.delete(game);
        return new GameResponse(winner.getName(), loser.getName());
    }

    public List<Piece> getFoesPieces(ChessGame game){
        return getOpponent(game).getPieces();
    }
    public List<Piece> getCurrentTeam(ChessGame game){
        return getCurrentPlayer(game).getPieces();
    }
    public Player getCurrentPlayer(ChessGame game){
        return game.isWhitesTurn() ? game.getWhitePlayer() : game.getBlackPlayer();
    }
    public Player getOpponent(ChessGame game){
        return game.isWhitesTurn() ? game.getBlackPlayer() : game.getWhitePlayer();
    }

    public List<Square> getAllSquares(ChessGame game){
        return game.getAllPieces().stream()
                .map(Piece::getSquare)
                .collect(Collectors.toList());
    }
//    public List<Spot> convertToSpots(Player player){
//        return player.getPieces().stream().map(Piece::getSpot).collect(Collectors.toList());
//    }
//    public boolean isOccupied(Game game, int location){
//        return getAllPieces(game).anyMatch(piece -> piece.isAtPosition(location));
//    }


    private Piece findPiece(ChessGame game, int position) {
        return getCurrentTeam(game).stream()
                .filter(piece -> piece.isAtPosition(position))
                .findAny().orElseThrow(PieceNotFound::new);
    }

    private Optional<Piece> findPiece(List<Piece> pieces, int position) {
        return pieces.stream()
                .filter(piece -> piece.isAtPosition(position))
                .findAny();
    }

    void removeFoeIfCaptured(ChessGame game, int end) {
        getFoesPieces(game).stream().filter(p -> p.isAtPosition(end)).findAny()
                .ifPresent(foe -> getFoesPieces(game).remove(foe));
    }

    private boolean isAttack(ChessGame game, int end) {
        return getFoesPieces(game).stream().anyMatch(p -> p.isAtPosition(end));
    }
    private Optional<Piece> isFoeCaptured(ChessGame game, int end) {
        return getFoesPieces(game).stream().filter(p -> p.isAtPosition(end)).findAny();
    }

}
