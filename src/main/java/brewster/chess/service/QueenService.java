package brewster.chess.service;

import brewster.chess.model.Piece;
import brewster.chess.model.Queen;
import org.springframework.stereotype.Service;

import java.awt.Point;
import java.util.List;
import java.util.stream.Stream;

@Service
public class QueenService implements PieceService<Queen> {
    @Override
    public List<Point> calculatePotentialMoves(Queen piece, Stream<Piece> allPieces) {
        int x = piece.getX();
        int y = piece.getY();
        if (isOccupied(x, ++y, allPieces)){
            return List.of();
        }
        return null;
    }
}
