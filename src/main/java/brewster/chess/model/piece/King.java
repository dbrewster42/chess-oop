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
    public List<Spot> calculateLegalMoves(List<Spot> allSpots, List<Piece> foes) {
        List<Spot> moves = new ArrayList<>();
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                moveOneSpot(moves, allSpots, foes, i, j);
            }
        }
        return moves;
    }

    @Override
    public boolean isLegalAttack(Spot destination, List<Spot> allSpots) {
        return Math.abs(destination.x - spot.x) < 2 && Math.abs(destination.y - spot.y) < 2;
    }

    private void moveOneSpot(List<Spot> moves, List<Spot> allSpots, List<Piece> foes, int xDirection, int yDirection){
        int x = spot.x + xDirection;
        int y = spot.y + yDirection;
        if (isOnBoard(x, y)){
            if (!isOccupied(x, y, allSpots)){
                moves.add(new Spot(x, y));
            } else if (isOpponent(foes, x, y)){
                moves.add(new Spot(x, y));
            }
        }
    }
}
