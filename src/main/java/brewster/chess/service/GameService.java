package brewster.chess.service;

import brewster.chess.exception.PieceNotFound;
import brewster.chess.model.Game;
import brewster.chess.model.Player;
import brewster.chess.model.request.PromotionRequest;
import brewster.chess.model.response.PieceResponse;
import brewster.chess.piece.Piece;
import brewster.chess.piece.PieceFactory;
import brewster.chess.piece.Queen;

//import brewster.chess.piece.Piece;

import java.awt.Point;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameService {

//    private final PieceService pieceService;
//
//    public GameService(PieceService pieceService) {
//        this.pieceService = pieceService;
//    }

    public List<Piece> getAllPieces(Game game){
        return Stream.concat(game.getPlayer1().getPieces().stream(), game.getPlayer2().getPieces().stream()).collect(Collectors.toList());
//        return player1.getPieces().stream().conc
    }
//    public List<Point> convertToSpots(Player player){
//        return player.getPieces().stream().map(Piece::getSpot).collect(Collectors.toList());
//    }
//    public boolean isOccupied(Game game, int location){
//        return getAllPieces(game).anyMatch(piece -> piece.isAtPosition(location));
//    }
    public List<Point> calculatePossibleMoves(Game game, int position) {
//        return getCurrentPlayer(game).getPieces().stream()
////                .filter(piece -> piece.getX() == position / 10 && piece.getY() == position % 10)
//                .filter(piece -> piece.isAtPosition(position))
//                .findAny()
////                .map(p -> convertToTypeT(p, p.getClass()))
////                .map(p -> pieceService.calculatePotentialMoves(p, getAllPieces(game)))
        return findPiece(game, position)
                .map(p -> p.calculatePotentialMoves(getAllPieces(game)))
                .orElseThrow();
        //        List<Point> occupiedSpots = getAllPieces(game).map(Piece::getSpot).collect(Collectors.toList());

//        return getCurrentPlayer(game).getPieces().stream()
//                .filter(piece -> piece.getLocation() == position)
//                .findAny()
//                .map(piece -> piece.calculatePotentialMoves(convertToSpots(getCurrentPlayer(game)), convertToSpots(game.getPlayer2())))//todo distinguish friend from foe
//                .orElseThrow();
    }

//    private <T extends Piece> T convertToTypeT(Piece p, Class<T> clazz) {
//        if (clazz.isInstance(p)){
//            return clazz.cast(p);
//        }
//        throw new RuntimeException("Cannot convert to subclass");
//    }
////    private

    public Player getCurrentPlayer(Game game){
        return game.isWhitesTurn() ? game.getPlayer1() : game.getPlayer2();
    }

    public List<PieceResponse> implementPromotion(Game game, PromotionRequest request) {
        List<Piece> pieces = getCurrentPlayer(game).getPieces();
        Piece piece = findPiece(pieces, request.getOldPosition()).orElseThrow(PieceNotFound::new);
        pieces.remove(piece);
        pieces.add(new PieceFactory(piece.getTeam(), request.getNewPosition(), request.getType()).getInstance());
//        pieces.add(new Queen(piece.getTeam(), request.getNewPosition() / 10, request.getNewPosition() % 10));
        return generatePieceResponse(game);
    }

    public List<PieceResponse> generatePieceResponse(Game game) {
        //todo
        return List.of();
    }

    private Optional<Piece> findPiece(Game game, int position) {
        return getCurrentPlayer(game).getPieces().stream()
                .filter(piece -> piece.isAtPosition(position))
                .findAny();
    }

    private Optional<Piece> findPiece(List<Piece> pieces, int position) {
        return pieces.stream()
                .filter(piece -> piece.isAtPosition(position))
                .findAny();
    }
}
