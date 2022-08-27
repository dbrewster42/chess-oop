package brewster.chess.piece;

import brewster.chess.model.constant.Team;

import java.awt.Point;
import java.util.List;

import static brewster.chess.model.constant.Type.QUEEN;

public class Queen extends Piece {
    public Queen(Team team, int x, int y) {
        super(team, x, y, QUEEN);
    }

    @Override
    public List<Point> calculatePotentialMoves(List<Piece> allPieces) {
        List<Point> potentialMoves = addUpAndDownMoves(allPieces);
        potentialMoves.addAll( addDiagonalMoves(allPieces));
        return potentialMoves;
//        List<Point> moves = new ArrayList<>();
//        for (int i = -1; i <= 1; i++){
//            for (int j = -1; j <= 1; j++){
//                addMovesAlongLine(allPieces, moves, i, j);
//            }
//        }
//        return moves;
    }

}
