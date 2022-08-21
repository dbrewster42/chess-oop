package brewster.chess.service;

//import brewster.chess.piece.Piece;

import brewster.chess.model.Piece;

import java.awt.Point;
import java.util.List;
import java.util.stream.Stream;

public interface PieceService<T> {
//    List<Point> calculatePotentialMoves(List<Point> friends, List<Point> foes);
    List<Point> calculatePotentialMoves(T piece, Stream<Piece> allPieces);
    default boolean isOccupied(int x, int y, Stream<Piece> pieces){
        return pieces.anyMatch(piece -> piece.getX() == x && piece.getY() == y);
    }
}
