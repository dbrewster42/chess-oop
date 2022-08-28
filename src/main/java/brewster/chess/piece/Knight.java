package brewster.chess.piece;

import brewster.chess.model.constant.Team;

import java.awt.Point;
import java.util.List;

import static brewster.chess.model.constant.Type.KNIGHT;

public class Knight extends Piece {
    public Knight(Team team, int x, int y) {
        super(team, x, y, KNIGHT);
    }

    @Override
    public List<Point> calculatePotentialMoves(List<Piece> allPieces) {
        return null;
    }
}
