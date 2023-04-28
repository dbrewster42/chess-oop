package brewster.chess.model.piece;

import brewster.chess.model.constant.Team;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

import static brewster.chess.model.constant.Type.ROOK;

@Entity
@NoArgsConstructor
public class Rook extends Piece {
    public Rook(Team team, int x, int y) {
        super(team, x, y, ROOK);
    }

    @Override
    public List<Square> calculateLegalMoves(List<Square> allSquares, List<Piece> foes) {
        return addUpAndDownMoves(allSquares, foes);
    }

    @Override
    public boolean isLegalAttack(Square destination, List<Square> allSquares) {
        return isOnStraightLine(destination, allSquares);
    }
}
