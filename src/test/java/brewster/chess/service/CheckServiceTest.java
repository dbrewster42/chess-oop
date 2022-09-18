package brewster.chess.service;

import brewster.chess.service.model.GamePiecesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brewster.chess.mother.PieceMother.createKingQueenDto;
import static brewster.chess.mother.PieceMother.createStalemateDto;
import static org.assertj.core.api.Assertions.assertThat;

class CheckServiceTest {
    CheckService sut = new CheckService();


    @BeforeEach
    void setUp() {
    }

    @Test
    void isStaleMate() {
        GamePiecesDto dto = createStalemateDto();

        assertThat(sut.isStaleMate(dto)).isTrue();
    }

    @Test
    void isNotStaleMate() {
        GamePiecesDto dto = createKingQueenDto();

        assertThat(sut.isStaleMate(dto)).isFalse();
    }


}