package brewster.chess.service;

import brewster.chess.exception.PieceNotFound;
import brewster.chess.model.Game;
import brewster.chess.player.Player;
import brewster.chess.model.User;
import brewster.chess.model.request.MoveRequest;
import brewster.chess.model.request.PromotionRequest;
import brewster.chess.model.response.GameResponse;
import brewster.chess.model.response.NewGameResponse;
import brewster.chess.piece.Piece;
import brewster.chess.piece.PieceFactory;
import brewster.chess.repository.GameRepository;

import java.awt.Point;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameService {
    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public NewGameResponse startGame(User user1) {
        //todo sockets find 2nd player
//        Game newGame = repository.save(new Game(user1, user2));

        return new NewGameResponse(1, null, null);
    }
    public NewGameResponse startGame(User user1, User user2) {
        Game newGame = repository.save(new Game(user1, user2));

        return new NewGameResponse(newGame.getId(), newGame.getPlayer1(), newGame.getPlayer2());
    }

    public List<Point> getLegalMoves(Game game, int position) {
        return findPiece(game, position)
                .calculateLegalMoves(getAllSpots(game), getFoesPieces(game));
    }

    public GameResponse movePiece(Game game, MoveRequest request) {
        removeFoeIfCaptured(game, request.getEnd());

        Piece piece = findPiece(game, request.getStart());
        piece.move(request.getEnd());
        game.setWhitesTurn(!game.isWhitesTurn());

        //todo 1. validate that it doesn't move into check 2. validate if created check 3. special moves?
        return getGameResponse(game);
    }

    public GameResponse implementPromotion(Game game, PromotionRequest request) {
        List<Piece> pieces = getCurrentPlayer(game).getPieces();
        Piece piece = findPiece(pieces, request.getOldPosition());
        pieces.remove(piece);
        pieces.add(new PieceFactory(piece.getTeam(), request.getNewPosition(), request.getType()).getInstance());
//        pieces.add(new Queen(piece.getTeam(), request.getNewPosition() / 10, request.getNewPosition() % 10));
        return getGameResponse(game);
    }


    private GameResponse getGameResponse(Game game) {
        //todo
        return new GameResponse(game, "generatedMessage");
    }

    public List<Piece> getAllPieces(Game game){
        return Stream.concat(game.getPlayer1().getPieces().stream(), game.getPlayer2().getPieces().stream()).collect(Collectors.toList());
    }
    public List<Piece> getFoesPieces(Game game){
        return getOpponent(game).getPieces();
    }

    public Stream<Piece> getAllPiecesStream(Game game){
        return Stream.concat(game.getPlayer1().getPieces().stream(), game.getPlayer2().getPieces().stream());
    }

    public List<Point> getAllSpots(Game game){
        return Stream.concat(game.getPlayer1().getPieces().stream(), game.getPlayer2().getPieces().stream())
                .map(Piece::getSpot)
                .collect(Collectors.toList());
    }
//    public List<Point> convertToSpots(Player player){
//        return player.getPieces().stream().map(Piece::getSpot).collect(Collectors.toList());
//    }
//    public boolean isOccupied(Game game, int location){
//        return getAllPieces(game).anyMatch(piece -> piece.isAtPosition(location));
//    }


    public Player getCurrentPlayer(Game game){
        return game.isWhitesTurn() ? game.getPlayer1() : game.getPlayer2();
    }
    public Player getOpponent(Game game){
        return game.isWhitesTurn() ? game.getPlayer2() : game.getPlayer1();
    }

    private Piece findPiece(Game game, int position) {
        return getCurrentPlayer(game).getPieces().stream()
                .filter(piece -> piece.isAtPosition(position))
                .findAny().orElseThrow(PieceNotFound::new);
    }

    private Piece findPiece(List<Piece> pieces, int position) {
        return pieces.stream()
                .filter(piece -> piece.isAtPosition(position))
                .findAny().orElseThrow(PieceNotFound::new);
    }

    private void removeFoeIfCaptured(Game game, int end) {
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
