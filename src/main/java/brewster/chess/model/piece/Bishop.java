package brewster.chess.model.piece;

import brewster.chess.model.constant.Team;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

import static brewster.chess.model.constant.Type.BISHOP;

@Entity
@NoArgsConstructor
public class Bishop extends Piece {
    public Bishop(Team team, int x, int y) {
        super(team, x, y, BISHOP);
    }

    @Override
    public List<Square> calculateLegalMoves(List<Square> allSquares, List<Piece> foes) {
        return addDiagonalMoves(allSquares, foes);
    }

    @Override
    public boolean isLegalAttack(Square destination, List<Square> allSquares) {
       return isOnDiagonalLine(destination, allSquares);
    }
}
