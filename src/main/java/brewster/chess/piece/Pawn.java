package brewster.chess.piece;

import java.awt.Point;
import java.util.List;

import static brewster.chess.piece.Type.PAWN;

public class Pawn extends Piece {
    private boolean isWhite;
    private boolean hasMoved = false;
    private int direction;

    public Pawn(int x, int y) {
        super(PAWN, x, y);
//        this.isWhite = y == 2;
        this.direction = y == 2 ? 1 : -1;
    }


    @Override
    public List<Point> calculatePotentialMoves(List<Point> friends, List<Point> foes) {
        if (direction == 1 && spot.y == 2 || direction == -1 && spot.y == 7) {

        }
//        int y = isWhite ? 1 : -1;
        return List.of(spot);
    }

}
