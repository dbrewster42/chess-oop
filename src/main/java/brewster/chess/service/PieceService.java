package brewster.chess.service;

import brewster.chess.model.Piece;
import java.awt.Point;
import java.util.List;
import java.util.stream.Stream;

    //todo make abstract class implement this class? or make MovesService that contains all but calculate method
public interface PieceService<T> {
    List<Point> calculatePotentialMoves(T piece, Stream<Piece> allPieces);
    default boolean isOccupied(int x, int y, Stream<Piece> pieces){
        return pieces.anyMatch(piece -> piece.getX() == x && piece.getY() == y);
    }
    private boolean isTeammate(Piece piece, Stream<Piece> allPieces, int x, int y){
        return allPieces.filter(p -> p.getX() == x && p.getY() == y).findAny().map(Piece::getTeam).equals(piece.getTeam());
    }

    default void addMovesAlongLine(Piece piece, Stream<Piece> allPieces, List<Point> moves, int xDirection, int yDirection) {
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
    default void addDiagonalMoves(Piece piece, Stream<Piece> allPieces, List<Point> moves){
        addMovesAlongLine(piece, allPieces, moves, -1, -1);
        addMovesAlongLine(piece, allPieces, moves, 1, 1);
        addMovesAlongLine(piece, allPieces, moves, 1, -1);
        addMovesAlongLine(piece, allPieces, moves, -1, 1);
    }

    default void addUpAndDownMoves(Piece piece, Stream<Piece> allPieces, List<Point> moves) {
        addMovesAlongLine(piece, allPieces, moves, -1, 0);
        addMovesAlongLine(piece, allPieces, moves, 1, 0);
        addMovesAlongLine(piece, allPieces, moves, 0, -1);
        addMovesAlongLine(piece, allPieces, moves, 0, 1);
    }
}
