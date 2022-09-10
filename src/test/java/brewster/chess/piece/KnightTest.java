package brewster.chess.piece;

import brewster.chess.model.constant.Team;
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

    @Test
    void calculateLegalMoves2(){
        knight.move(44);
        List<Point> legalMoves = knight.calculateLegalMoves(allSpots, foes);
        assertThat(legalMoves).containsExactlyInAnyOrder(new Point(3,2), new Point(2,3), new Point(6,3),
                new Point(5,6), new Point(6,5), new Point(3,6), new Point(2,5), new Point(5,2));
    }

    @Test
    void calculateLegalMoves3(){
        knight.move(44);
        allSpots.add(new Point(3,2));
        allSpots.add(new Point(2,3));
        foes.add(new Pawn(Team.BLACK, 2, 3));
        List<Point> legalMoves = knight.calculateLegalMoves(allSpots, foes);
        assertThat(legalMoves).containsExactlyInAnyOrder(new Point(2,3), new Point(6,3),
                new Point(5,6), new Point(6,5), new Point(3,6), new Point(2,5), new Point(5,2));
    }

    @Test
    void isLegalAttack() {
        knight.move(42);
        assertThat(knight.isLegalAttack(new Point(6, 3), allSpots)).isTrue();
        assertThat(knight.isLegalAttack(new Point(5, 4), allSpots)).isTrue();
        assertThat(knight.isLegalAttack(new Point(6, 4), allSpots)).isFalse();
        assertThat(knight.isLegalAttack(new Point(7, 2), allSpots)).isFalse();
        assertThat(knight.isLegalAttack(new Point(2, 1), allSpots)).isTrue();
        assertThat(knight.isLegalAttack(new Point(3, 4), allSpots)).isTrue();
        assertThat(knight.isLegalAttack(new Point(5, 3), allSpots)).isFalse();
        assertThat(knight.isLegalAttack(new Point(7, 5), allSpots)).isFalse();
    }
}
