package brewster.chess.piece;

import brewster.chess.model.constant.Team;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.awt.Point;
import java.util.List;

import static brewster.chess.model.constant.Type.BISHOP;

@Entity
@NoArgsConstructor
public class Bishop extends Piece {
    public Bishop(Team team, int x, int y) {
        super(team, x, y, BISHOP);
    }

    @Override
    public List<Point> calculateLegalMoves(List<Point> allSpots, List<Piece> foes) {
        return addDiagonalMoves(allSpots, foes);
    }

    @Override
    public boolean isLegalAttack(Point destination, List<Point> allSpots) {
       return isOnDiagonalLine(destination, allSpots);
    }
}
