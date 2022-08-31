package brewster.chess.piece;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.List;

import static brewster.chess.mother.PieceMother.getFoes;
import static brewster.chess.mother.PieceMother.getSpots2;
import static brewster.chess.mother.PieceMother.getWhiteKnight;
import static org.assertj.core.api.Assertions.assertThat;

public class KnightTest {
    Piece knight;
    List<Point> allSpots;
    List<Piece> foes;


    @BeforeEach
    void setup(){
        knight = getWhiteKnight();
        allSpots = getSpots2();
        foes = getFoes();
    }

    @Test
    void calculateLegalMoves(){
        knight.move(21);
        List<Point> legalMoves = knight.calculateLegalMoves(allSpots, foes);
        assertThat(legalMoves).containsExactlyInAnyOrder(new Point(4,2), new Point(3,3), new Point(1,3));
    }

}
