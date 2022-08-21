package brewster.chess.service;

//import brewster.chess.piece.Piece;

import brewster.chess.model.Piece;
import brewster.chess.model.Queen;

import java.awt.Point;
import java.util.List;
import java.util.stream.Stream;

public interface PieceService<T> {
//    List<Point> calculatePotentialMoves(List<Point> friends, List<Point> foes);
    List<Point> calculatePotentialMoves(T piece, Stream<Piece> allPieces);
    default boolean isOccupied(int x, int y, Stream<Piece> pieces){
        return pieces.anyMatch(piece -> piece.getX() == x && piece.getY() == y);
    }
    default void addMovesAlongLine(Queen piece, Stream<Piece> allPieces, List<Point> moves, int xDirection, int yDirection) {
        int x = piece.getX();
        int y = piece.getY();
        while (x > 0 && x < 9 && y > 0 && y < 9){
            x += xDirection;
            y += yDirection;
            if (!isOccupied(x, y, allPieces)){
                moves.add(new Point(x, y));
            } else {
                if (!isTeammate(piece, allPieces, x, y)){
                    moves.add(new Point(x, y));
                }
                break;
            }
        }
    }

    private boolean isTeammate(Queen piece, Stream<Piece> allPieces, int x, int y){
//        Piece destination = allPieces.filter(p -> p.getX() == x && p.getY() == y).findAny().orElseThrow();
//        return destination.getTeam().equals(piece.getTeam());
        return allPieces.filter(p -> p.getX() == x && p.getY() == y).findAny().map(Piece::getTeam).equals(piece.getTeam());
    }
}
