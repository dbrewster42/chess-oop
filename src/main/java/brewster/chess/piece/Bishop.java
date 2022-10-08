package brewster.chess.piece;

import brewster.chess.model.constant.Team;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import brewster.chess.piece.Spot;
import java.util.List;

import static brewster.chess.model.constant.Type.BISHOP;

@Entity
@NoArgsConstructor
public class Bishop extends Piece {
    public Bishop(Team team, int x, int y) {
        super(team, x, y, BISHOP);
    }

    @Override
    public List<Spot> calculateLegalMoves(List<Spot> allSpots, List<Piece> foes) {
        return addDiagonalMoves(allSpots, foes);
    }

    @Override
    public boolean isLegalAttack(Spot destination, List<Spot> allSpots) {
       return isOnDiagonalLine(destination, allSpots);
    }
}
