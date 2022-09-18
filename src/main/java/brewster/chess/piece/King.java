package brewster.chess.piece;

import brewster.chess.model.constant.Team;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import static brewster.chess.model.constant.Type.KING;


public class King extends Piece {
    public King(Team team, int x, int y) {
        super(team, x, y, KING);
    }

    @Override
    public List<Point> calculateLegalMoves(List<Point> allSpots, List<Piece> foes) {
        List<Point> moves = new ArrayList<>();
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                moveOneSpot(moves, allSpots, foes, i, j);
            }
        }
        return moves;
    }

    @Override
    public boolean isLegalAttack(Point destination, List<Point> allSpots) {
        return Math.abs(destination.x - spot.x) < 2 && Math.abs(destination.y - spot.y) < 2;
    }

    private void moveOneSpot(List<Point> moves, List<Point> allSpots, List<Piece> foes, int xDirection, int yDirection){
        int x = spot.x + xDirection;
        int y = spot.y + yDirection;
        if (isOnBoard(x, y)){
            if (!isOccupied(x, y, allSpots)){
                moves.add(new Point(x, y));
            } else if (isOpponent(foes, x, y)){
                moves.add(new Point(x, y));
            }
        }
    }
}
