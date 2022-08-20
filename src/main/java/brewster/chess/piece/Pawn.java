package brewster.chess.piece;

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
    public int[] calculatePotentialMoves() {
        if (direction == 1 && this.y == 2 || direction == -1 && this.y == 7) {

        }
//        int y = isWhite ? 1 : -1;
        return new int[0];
    }

}
