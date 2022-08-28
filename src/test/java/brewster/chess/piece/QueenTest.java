package brewster.chess.piece;

import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.List;

import static brewster.chess.mother.PieceMother.getAllPieces;
import static brewster.chess.mother.PieceMother.getWhiteKing;
import static brewster.chess.mother.PieceMother.getWhiteQueen;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

    Piece queen = getWhiteQueen();
    List<Piece> allPieces = getAllPieces();

    @Test
    void calculatePotentialMoves() {
        List<Point> legalMoves = queen.calculatePotentialMoves(allPieces);
        assertThat(legalMoves.size()).isEqualTo(17);
    }

    @Test
    void calculatePotentialMoves2() {
        allPieces.get(3).move(42);
        List<Point> legalMoves = queen.calculatePotentialMoves(allPieces);
        assertThat(legalMoves.size()).isEqualTo(11);
    }
}