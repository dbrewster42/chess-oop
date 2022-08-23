package brewster.chess.model.piece;

import brewster.chess.model.Piece;
import brewster.chess.model.constant.Team;
import brewster.chess.service.PieceService;

import java.awt.Point;
import java.util.List;
import java.util.stream.Stream;


public class King extends Piece {
    public King(Team team, int x, int y) {
        super(team, x, y);
    }

    @Override
    public List<Point> calculatePotentialMoves(Stream<Piece> allPieces) {
        PieceService pieceService = new PieceService();
        return pieceService.calculatePotentialMoves(this, allPieces);
    }
}
