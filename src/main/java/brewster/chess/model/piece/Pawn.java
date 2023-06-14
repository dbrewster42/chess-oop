package brewster.chess.model.piece;

import brewster.chess.model.constant.Team;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

import static brewster.chess.model.constant.Team.WHITE;
import static brewster.chess.model.constant.Type.PAWN;

@Entity
@NoArgsConstructor
public class Pawn extends Piece {
    public boolean canPromote = false;

    public Pawn(Team team, int x, int y) {
        super(team, x, y, PAWN);
    }


    @Override
    public List<Square> calculateLegalMoves(List<Square> squares, List<Piece> foes) {
        List<Square> legalMoves = new ArrayList<>();
        int direction = getDirection();
        int x = square.x;
        int y = square.y + direction;
        if (y == 1 || y == 8) { canPromote = true; }
        if (!isOccupied(x, y, squares)){
            legalMoves.add(new Square(x, y));
            if (hasNotMoved(direction)){
                if (!isOccupied(x, y + direction, squares)) {
                    legalMoves.add(new Square(x, y + direction));
                }
            }
        }
        addValidAttacks(legalMoves, squares, foes, x, y);

        return legalMoves;
    }

    @Override
    public boolean isLegalAttack(Square destination, List<Square> allSquares) {
        return destination.y - square.y == getDirection() && Math.abs(destination.x - square.x) == 1;
    }

    @Override
    public boolean isLegalBlock(Square destination, List<Square> allSquares) {
        int direction = getDirection();
        return destination.y - square.y == direction || (hasNotMoved(direction) && destination.y - square.y == direction * 2) ;
    }
//    @Override
//    public boolean isLegalMove(Square destination, List<Square> allSquares) { //todo probably best to use calculateLegalMoves instead
////        int direction = getDirection();
////        return destination.y - square.y == direction || (hasNotMoved(direction) && destination.y - square.y == direction * 2) ;
//        return calculateLegalMoves(allSquares)
//    }

    private int getDirection(){
        return team == WHITE ? 1 : -1;
    }

    private boolean hasNotMoved(int direction){
        return (direction == 1 && getSquare().y == 2) || (direction == -1 && getSquare().y == 7);
    }

    private void addValidAttacks(List<Square> moves, List<Square> squares, List<Piece> foes, int x, int y){
        addAttackIfValid(moves, squares, foes, x - 1, y);
        addAttackIfValid(moves, squares, foes, x + 1, y);
    }
    private void addAttackIfValid(List<Square> moves, List<Square> squares, List<Piece> foes, int x, int y){
        if (isOccupied(x, y, squares) && isOpponent(foes, x, y)){
            moves.add(new Square(x, y));
        }
    }
}
