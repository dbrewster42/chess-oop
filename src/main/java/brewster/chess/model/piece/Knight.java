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
    public List<Spot> calculateLegalMoves(List<Spot> allSpots, List<Piece> foes) {
        List<Spot> moves = new ArrayList<>();
        for (int i = -1; i <= 1; i += 2){
            for (int j = -1; j <= 1; j += 2){
                makeJump(moves, allSpots, foes, i, j);
            }
        }
        return moves;
    }

    @Override
    public boolean isLegalAttack(Spot destination, List<Spot> allSpots) {
        int xDiff = Math.abs(destination.x - spot.x);
        return ((xDiff == 1 || xDiff == 2) && xDiff + Math.abs(destination.y - spot.y) == 3);
    }

    private void makeJump(List<Spot> moves, List<Spot> spots, List<Piece> foes, int xDirection, int yDirection){
        addLegalMove(moves, spots, foes, 2 * xDirection + spot.x,  yDirection + spot.y);
        addLegalMove(moves, spots, foes, xDirection + spot.x,  2 * yDirection + spot.y);
    }

    private void addLegalMove(List<Spot> moves, List<Spot> spots, List<Piece> foes, int x, int y){
        if (isOnBoard(x, y)){
            if (!isOccupied(x, y, spots) || isOpponent(foes, x, y)){
                moves.add(new Spot(x, y));
            }
        }
    }
}
