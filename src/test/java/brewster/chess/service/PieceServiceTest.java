package brewster.chess.service;

import brewster.chess.model.Piece;
import brewster.chess.model.constant.Team;
import brewster.chess.model.piece.Queen;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static brewster.chess.model.constant.Type.QUEEN;
import static org.junit.jupiter.api.Assertions.*;

class PieceServiceTest {
    private PieceService sut = new PieceService();
    @Test
    void calculatePotentialMoves() {
        Piece queen = new Queen(Team.WHITE, 1, 1);
        sut.calculatePotentialMoves((Queen) queen, Stream.of(queen));
    }

    @Test
    void testCalculatePotentialMoves() {
    }
}