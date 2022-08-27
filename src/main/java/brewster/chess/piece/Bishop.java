package brewster.chess.piece;

import brewster.chess.model.constant.Team;

import java.awt.Point;
import java.util.List;
import java.util.stream.Stream;

public class Bishop extends Piece {
    public Bishop(Team team, int x, int y) {
        super(team, x, y);
    }

    @Override
    public List<Point> calculatePotentialMoves(Stream<Piece> allPieces) {
        return addDiagonalMoves(allPieces);
    }
}
