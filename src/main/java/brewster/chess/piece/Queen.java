package brewster.chess.piece;

import brewster.chess.model.constant.Team;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.awt.Point;
import java.util.List;

import static brewster.chess.model.constant.Type.QUEEN;

@Entity
public class Queen extends Piece {
    public Queen(Team team, int x, int y) {
        super(team, x, y, QUEEN);
    }

    @Override
    public List<Point> calculateLegalMoves(List<Point> allSpots, List<Piece> foes) {
        List<Point> potentialMoves = addUpAndDownMoves(allSpots, foes);
        potentialMoves.addAll(addDiagonalMoves(allSpots, foes));
        return potentialMoves;
    }

    @Override
    public boolean isLegalAttack(Point destination, List<Point> allSpots) {
        return isOnDiagonalLine(destination, allSpots) || isOnStraightLine(destination, allSpots);
    }

}
