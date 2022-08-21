package brewster.chess.piece;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void hasMoved() {
        Pawn pawn = new Pawn(1, 2);
        assertThat(pawn.hasMoved()).isFalse();
        pawn.makeMove(13);
        assertThat(pawn.hasMoved()).isTrue();
        pawn.makeMove(17);
        assertThat(pawn.hasMoved()).isTrue();
    }
}