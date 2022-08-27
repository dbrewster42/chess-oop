package brewster.chess.piece;

import brewster.chess.model.constant.Team;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PawnTest {

    @Test
    void hasMoved() {
        Pawn pawn = new Pawn(Team.WHITE, 1, 2);
        assertThat(pawn.hasMoved()).isFalse();
        pawn.move(13);
        assertThat(pawn.hasMoved()).isTrue();
        pawn.move(17);
        assertThat(pawn.hasMoved()).isTrue();
    }
}