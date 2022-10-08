package brewster.chess.piece;

import brewster.chess.model.constant.Team;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import brewster.chess.piece.Spot;
import java.util.List;

import static brewster.chess.model.constant.Type.ROOK;

@Entity
@NoArgsConstructor
public class Rook extends Piece {
    public Rook(Team team, int x, int y) {
        super(team, x, y, ROOK);
    }

    @Override
    public List<Spot> calculateLegalMoves(List<Spot> allSpots, List<Piece> foes) {
        return addUpAndDownMoves(allSpots, foes);
    }

    @Override
    public boolean isLegalAttack(Spot destination, List<Spot> allSpots) {
        return isOnStraightLine(destination, allSpots);
    }
}
