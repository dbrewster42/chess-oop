package brewster.chess.service;

import brewster.chess.model.Piece;
import brewster.chess.model.piece.Rook;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RookService implements PieceService<Rook> {
    @Override
    public List<Point> calculatePotentialMoves(Rook piece, Stream<Piece> allPieces) {
        List<Point> potentialMoves = new ArrayList<>();
        addUpAndDownMoves(piece, allPieces, potentialMoves);
        return potentialMoves;
    }
}
