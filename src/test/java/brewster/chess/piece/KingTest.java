package brewster.chess.piece;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static brewster.chess.model.constant.Team.WHITE;
import static brewster.chess.mother.PieceMother.getBlackKing;
import static brewster.chess.mother.PieceMother.getBlackPawn;
import static brewster.chess.mother.PieceMother.getWhiteKing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class KingTest {
    Piece king = getWhiteKing();

    @Test
    void calculatePotentialMoves() {
        assertThat(king.calculatePotentialMoves(getPieces().get()).size()).isEqualTo(5);
        king.move(42);
        assertThat(king.calculatePotentialMoves(getPieces().get()).size()).isEqualTo(8);
        king.move(88);
        assertThat(king.calculatePotentialMoves(getPieces().get()).size()).isEqualTo(3);
        king.move(81);
        assertThat(king.calculatePotentialMoves(getPieces().get()).size()).isEqualTo(2);
    }

    private Supplier<Stream<Piece>> getPieces(){
        return () -> Stream.of(king, getBlackKing(), getBlackPawn());
    }
}