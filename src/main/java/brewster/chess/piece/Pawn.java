package brewster.chess.piece;

import brewster.chess.model.constant.Team;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import static brewster.chess.model.constant.Team.WHITE;
import static brewster.chess.model.constant.Type.PAWN;

public class Pawn extends Piece {
    private final int direction;

    public Pawn(Team team, int x, int y) {
        super(team, x, y, PAWN);
        this.direction = team == WHITE ? 1 : -1;
    }

    @Override
    public void move(int x, int y) {
        if (y == 0 || y == 8){
            throw new Promotion(spot, x, y);
        }
        spot.move(x, y);
    }

    @Override
    public List<Point> calculatePotentialMoves(List<Piece> pieces) {
        List<Point> legalMoves = new ArrayList<>();
        int x = spot.x;
        int y = spot.y + direction;
        if (!isOccupied(x, y, pieces)){
            legalMoves.add(new Point(x, y));
            if (!hasMoved()){
                if (!isOccupied(x, y + direction, pieces)) {
                    legalMoves.add(new Point(x, y + direction));
                }
            }
        }
        if (isOccupied(x - 1, y, pieces)){
            legalMoves.add(new Point(x - 1, y));
        }
        if (isOccupied(x + 1, y, pieces)){
            legalMoves.add(new Point(x + 1, y));
        }

        return legalMoves;
    }

    boolean hasMoved(){
        return (direction != 1 || getSpot().y != 2) && (direction != -1 || getSpot().y != 7);
    }

}
