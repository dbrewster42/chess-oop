package brewster.chess.piece;

public interface Piece {
    int getLocation();
    Type getType();
    String getTeam();
    int[] calculatePotentialMoves();
    void makeMove();
}
