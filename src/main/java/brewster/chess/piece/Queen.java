package brewster.chess.piece;

import brewster.chess.model.constant.Team;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import brewster.chess.piece.Spot;
import java.util.List;

import static brewster.chess.model.constant.Type.QUEEN;

@Entity
@NoArgsConstructor
public class Queen extends Piece {
    public Queen(Team team, int x, int y) {
        super(team, x, y, QUEEN);
    }

    @Override
    public List<Spot> calculateLegalMoves(List<Spot> allSpots, List<Piece> foes) {
        List<Spot> potentialMoves = addUpAndDownMoves(allSpots, foes);
        potentialMoves.addAll(addDiagonalMoves(allSpots, foes));
        return potentialMoves;
    }

    @Override
    public boolean isLegalAttack(Spot destination, List<Spot> allSpots) {
        return isOnDiagonalLine(destination, allSpots) || isOnStraightLine(destination, allSpots);
    }

}
