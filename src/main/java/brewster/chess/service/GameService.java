package brewster.chess.service;

import brewster.chess.model.Game;
import brewster.chess.model.Player;
import brewster.chess.piece.Piece;

//import brewster.chess.piece.Piece;

import java.awt.Point;
import java.util.List;
import java.util.stream.Stream;

public class GameService {

//    private final PieceService pieceService;
//
//    public GameService(PieceService pieceService) {
//        this.pieceService = pieceService;
//    }

    public Stream<Piece> getAllPieces(Game game){
        return Stream.concat(game.getPlayer1().getPieces().stream(), game.getPlayer2().getPieces().stream());
//        return player1.getPieces().stream().conc
    }
//    public List<Point> convertToSpots(Player player){
//        return player.getPieces().stream().map(Piece::getSpot).collect(Collectors.toList());
//    }
//    public boolean isOccupied(Game game, int location){
//        return getAllPieces(game).anyMatch(piece -> piece.isAtPosition(location));
//    }
    public List<Point> calculatePossibleMoves(Game game, int position) {



        return getCurrentPlayer(game).getPieces().stream()
//                .filter(piece -> piece.getX() == position / 10 && piece.getY() == position % 10)
                .filter(piece -> piece.isAtPosition(position))
                .findAny()
//                .map(p -> convertToTypeT(p, p.getClass()))
//                .map(p -> pieceService.calculatePotentialMoves(p, getAllPieces(game)))
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
}
