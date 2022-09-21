package brewster.chess.piece;

import brewster.chess.model.constant.Type;
import org.junit.jupiter.api.Test;

import static brewster.chess.model.constant.Type.BISHOP;
import static org.assertj.core.api.Assertions.assertThat;

class TypeTest {
    @Test
    void testType(){
        assertThat(BISHOP.name).isEqualTo("Bishop");
        assertThat(Type.valueOf("BISHOP")).isEqualTo(BISHOP);
    }

}