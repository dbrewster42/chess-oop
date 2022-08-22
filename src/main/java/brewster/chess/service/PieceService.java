package brewster.chess.service;

import brewster.chess.model.Piece;
import brewster.chess.model.piece.King;
import brewster.chess.model.piece.Queen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

    //todo make abstract class implement this class? or make MovesService that contains all but calculate method
public class PieceService {
    public List<Point> calculatePotentialMoves(Queen piece, Stream<Piece> allPieces){
        List<Point> potentialMoves = new ArrayList<>();
        addUpAndDownMoves(piece, allPieces, potentialMoves);
        addDiagonalMoves(piece, allPieces, potentialMoves);
        return potentialMoves;
    }
    public List<Point> calculatePotentialMoves(King piece, Stream<Piece> allPieces){
        List<Point> potentialMoves = new ArrayList<>();
        addUpAndDownMoves(piece, allPieces, potentialMoves);
        addDiagonalMoves(piece, allPieces, potentialMoves);
        return potentialMoves;
    }
    public boolean isOccupied(int x, int y, Stream<Piece> pieces){
        return pieces.anyMatch(piece -> piece.getX() == x && piece.getY() == y);
    }
    private boolean isTeammate(Piece piece, Stream<Piece> allPieces, int x, int y){
        return allPieces.filter(p -> p.getX() == x && p.getY() == y).findAny().map(Piece::getTeam).equals(piece.getTeam());
    }

    public void addMovesAlongLine(Piece piece, Stream<Piece> allPieces, List<Point> moves, int xDirection, int yDirection) {
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
    public void addDiagonalMoves(Piece piece, Stream<Piece> allPieces, List<Point> moves){
        addMovesAlongLine(piece, allPieces, moves, -1, -1);
        addMovesAlongLine(piece, allPieces, moves, 1, 1);
        addMovesAlongLine(piece, allPieces, moves, 1, -1);
        addMovesAlongLine(piece, allPieces, moves, -1, 1);
    }

    public void addUpAndDownMoves(Piece piece, Stream<Piece> allPieces, List<Point> moves) {
        addMovesAlongLine(piece, allPieces, moves, -1, 0);
        addMovesAlongLine(piece, allPieces, moves, 1, 0);
        addMovesAlongLine(piece, allPieces, moves, 0, -1);
        addMovesAlongLine(piece, allPieces, moves, 0, 1);
    }
}
