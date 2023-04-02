package brewster.chess.model.piece;

import brewster.chess.exception.Promotion;
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

    public Pawn(Team team, int x, int y) {
        super(team, x, y, PAWN);
    }

//    @Override
//    public void move(int newSpot) {
//        int x = newSpot / 10;
//        int y = newSpot % 10;
////        if (y == 1 || y == 8){
////            throw new Promotion(spot, x, y);
////        }
//        spot.move(newSpot);
//    }

    @Override
    public List<Spot> calculateLegalMoves(List<Spot> spots, List<Piece> foes) {
        List<Spot> legalMoves = new ArrayList<>();
        int direction = getDirection();
        int x = spot.x;
        int y = spot.y + direction;

        if (!isOccupied(x, y, spots)){
            legalMoves.add(new Spot(x, y));
            if (hasNotMoved(direction)){
                if (!isOccupied(x, y + direction, spots)) {
                    legalMoves.add(new Spot(x, y + direction));
                }
            }
        }
        addIfAttackPossible(legalMoves, spots, foes, x - 1, y);
        addIfAttackPossible(legalMoves, spots, foes, x + 1, y);

        return legalMoves;
    }

    @Override
    public boolean isLegalAttack(Spot destination, List<Spot> allSpots) {
        return destination.y - spot.y == getDirection() && Math.abs(destination.x - spot.x) == 1;
    }

    private int getDirection(){
        return team == WHITE ? 1 : -1;
    }

    private boolean hasNotMoved(int direction){
        return (direction == 1 && getSpot().y == 2) || (direction == -1 && getSpot().y == 7);
    }

    private void addIfAttackPossible(List<Spot> moves, List<Spot> spots, List<Piece> foes, int x, int y){
        if (isOccupied(x, y, spots) && isOpponent(foes, x, y)){
            moves.add(new Spot(x, y));
        }
    }


}
