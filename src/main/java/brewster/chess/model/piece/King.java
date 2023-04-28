package brewster.chess.model.piece;

import brewster.chess.model.constant.Team;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

import static brewster.chess.model.constant.Type.KING;

@Entity
@NoArgsConstructor
public class King extends Piece {
    public King(Team team, int x, int y) {
        super(team, x, y, KING);
    }

    @Override
    public List<Square> calculateLegalMoves(List<Square> allSquares, List<Piece> foes) {
        List<Square> moves = new ArrayList<>();
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                moveOneSpot(moves, allSquares, foes, i, j);
            }
        }
        return moves;
    }

    @Override
    public boolean isLegalAttack(Square destination, List<Square> allSquares) {
        return Math.abs(destination.x - square.x) < 2 && Math.abs(destination.y - square.y) < 2;
    }

    private void moveOneSpot(List<Square> moves, List<Square> allSquares, List<Piece> foes, int xDirection, int yDirection){
        int x = square.x + xDirection;
        int y = square.y + yDirection;
        if (isOnBoard(x, y)){
            if (!isOccupied(x, y, allSquares)){
                moves.add(new Square(x, y));
            } else if (isOpponent(foes, x, y)){
                moves.add(new Square(x, y));
            }
        }
    }
}
