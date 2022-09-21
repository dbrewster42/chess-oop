package brewster.chess.piece;

import brewster.chess.model.constant.Team;

import javax.persistence.Entity;
import java.awt.Point;
import java.util.List;

import static brewster.chess.model.constant.Type.ROOK;

@Entity
public class Rook extends Piece {
    public Rook(Team team, int x, int y) {
        super(team, x, y, ROOK);
    }

    @Override
    public List<Point> calculateLegalMoves(List<Point> allSpots, List<Piece> foes) {
        return addUpAndDownMoves(allSpots, foes);
    }

    @Override
    public boolean isLegalAttack(Point destination, List<Point> allSpots) {
        return isOnStraightLine(destination, allSpots);
    }
}
