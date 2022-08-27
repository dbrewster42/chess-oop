package brewster.chess.piece;

import brewster.chess.model.constant.Team;

import java.awt.Point;
import java.util.List;

import static brewster.chess.model.constant.Type.ROOK;

public class Rook extends Piece {
    public Rook(Team team, int x, int y) {
        super(team, x, y, ROOK);
    }

    @Override
    public List<Point> calculatePotentialMoves(List<Piece> allPieces) {
        return addUpAndDownMoves(allPieces);
    }
}
