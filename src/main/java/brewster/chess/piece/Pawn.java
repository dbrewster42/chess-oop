package brewster.chess.piece;

public class Pawn implements Piece {
    @Override
    public int getLocation() {
        return 0;
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public String getTeam() {
        return null;
    }

    @Override
    public int[] calculatePotentialMoves() {
        return new int[0];
    }

    @Override
    public void makeMove() {

    }
}
