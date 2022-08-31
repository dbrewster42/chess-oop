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
    public void move(int newSpot) {
        int x = newSpot / 10;
        int y = newSpot % 10;
        if (y == 1 || y == 8){
            throw new Promotion(spot, x, y);
        }
        spot.move(x, y);
    }

    @Override
    public List<Point> calculateLegalMoves(List<Point> spots, List<Piece> foes) {
        List<Point> legalMoves = new ArrayList<>();
        int x = spot.x;
        int y = spot.y + direction;
        if (!isOccupied(x, y, spots)){
            legalMoves.add(new Point(x, y));
            if (hasNotMoved()){
                if (!isOccupied(x, y + direction, spots)) {
                    legalMoves.add(new Point(x, y + direction));
                }
            }
        }
        addIfAttackPossible(legalMoves, spots, foes, x - 1, y);
        addIfAttackPossible(legalMoves, spots, foes, x + 1, y);

        return legalMoves;
    }

    private void addIfAttackPossible(List<Point> moves, List<Point> spots, List<Piece> foes, int x, int y){
        if (isOccupied(x, y, spots) && isOpponent(foes, x, y)){
            moves.add(new Point(x, y));
        }
    }

    boolean hasNotMoved(){
        return (direction == 1 && getSpot().y == 2) || (direction == -1 && getSpot().y == 7);
    }

}
