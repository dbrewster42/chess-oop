package brewster.chess.service;

import brewster.chess.model.Piece;
import brewster.chess.model.piece.Queen;
import org.springframework.stereotype.Service;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//@Service
//public class QueenService implements PieceService<Queen> {
//    @Override
//    public List<Point> calculatePotentialMoves(Queen piece, Stream<Piece> allPieces) {
//        List<Point> potentialMoves = new ArrayList<>();
//        addUpAndDownMoves(piece, allPieces, potentialMoves);
//        addDiagonalMoves(piece, allPieces, potentialMoves);
//        return potentialMoves;
//    }
//
//}
