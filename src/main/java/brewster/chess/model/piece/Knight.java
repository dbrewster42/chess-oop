package brewster.chess.model.piece;

import brewster.chess.model.constant.Team;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

import static brewster.chess.model.constant.Type.KNIGHT;

@Entity
@NoArgsConstructor
public class Knight extends Piece {
    public Knight(Team team, int x, int y) {
        super(team, x, y, KNIGHT);
    }

    @Override
    public List<Square> calculateLegalMoves(List<Square> allSquares, List<Piece> foes) {
        List<Square> moves = new ArrayList<>();
        for (int i = -1; i <= 1; i += 2){
            for (int j = -1; j <= 1; j += 2){
                makeJump(moves, allSquares, foes, i, j);
            }
        }
        return moves;
    }

    @Override
    public boolean isLegalAttack(Square destination, List<Square> allSquares) {
        int xDiff = Math.abs(destination.x - square.x);
        return ((xDiff == 1 || xDiff == 2) && xDiff + Math.abs(destination.y - square.y) == 3);
    }

    private void makeJump(List<Square> moves, List<Square> squares, List<Piece> foes, int xDirection, int yDirection){
        addLegalMove(moves, squares, foes, 2 * xDirection + square.x,  yDirection + square.y);
        addLegalMove(moves, squares, foes, xDirection + square.x,  2 * yDirection + square.y);
    }

    private void addLegalMove(List<Square> moves, List<Square> squares, List<Piece> foes, int x, int y){
        if (isOnBoard(x, y)){
            if (!isOccupied(x, y, squares) || isOpponent(foes, x, y)){
                moves.add(new Square(x, y));
            }
        }
    }
}
