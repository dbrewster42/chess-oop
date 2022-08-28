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
    public List<Point> calculatePotentialMoves(List<Piece> allPieces) {
        List<Point> moves = new ArrayList<>();
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                moveOneSpot(allPieces, moves, i, j);
            }
        }
        return moves;
    }

    private void moveOneSpot(List<Piece> allPieces, List<Point> moves, int xDirection, int yDirection){
        int x = spot.x + xDirection;
        int y = spot.y + yDirection;
        if (isOnBoard(x, y)){
            if (!isOccupied(x, y, allPieces)){
                moves.add(new Point(x, y));
            } else if (!isTeammate(getTeam(), allPieces, x, y)){
                moves.add(new Point(x, y));
            }
        }

    }
}
