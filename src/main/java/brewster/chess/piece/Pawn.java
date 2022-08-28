package brewster.chess.piece;

import brewster.chess.model.constant.Team;

import java.awt.Point;
import java.util.List;
import java.util.stream.Stream;

import static brewster.chess.model.constant.Team.WHITE;
import static brewster.chess.model.constant.Type.PAWN;

public class Pawn extends Piece {
    private boolean isWhite;
    private boolean hasMoved = false;
    private int direction;

    public Pawn(Team team, int x, int y) {
        super(team, x, y, PAWN);
        this.direction = team == WHITE ? 1 : -1;
    }

//    public Pawn(int x, int y) {
//        super(PAWN, x, y);
////        this.isWhite = y == 2;
//        this.direction = y == 2 ? 1 : -1;
//    }


    @Override
    public List<Point> calculatePotentialMoves(List<Piece> allPieces) {
        int x = getSpot().x;
        int y = getSpot().y;
        if (direction == 1 && y == 2 || direction == -1 && y == 7) {
            return List.of();
        }
//        int y = isWhite ? 1 : -1;
        return List.of(null);
    }

    boolean hasMoved(){
        return (direction != 1 || getSpot().y != 2) && (direction != -1 || getSpot().y != 7);
    }

}
