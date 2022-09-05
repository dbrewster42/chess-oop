package brewster.chess.piece;

import brewster.chess.model.constant.Team;

import java.awt.Point;
import java.util.List;

import static brewster.chess.model.constant.Type.BISHOP;

public class Bishop extends Piece {
    public Bishop(Team team, int x, int y) {
        super(team, x, y, BISHOP);
    }

    @Override
    public List<Point> calculateLegalMoves(List<Point> allSpots, List<Piece> foes) {
        return addDiagonalMoves(allSpots, foes);
    }
}
