package brewster.chess.piece;

import brewster.chess.model.constant.Type;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TypeTest {
    @Test
    void testType(){
        assertThat(Type.BISHOP.getName()).isEqualTo("Bishop");
    }

}