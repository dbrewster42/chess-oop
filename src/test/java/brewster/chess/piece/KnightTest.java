package brewster.chess.piece;

import brewster.chess.model.constant.Team;
import brewster.chess.model.piece.Pawn;
import brewster.chess.model.piece.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import brewster.chess.model.piece.Spot;
import java.util.List;

import static brewster.chess.mother.PieceMother.getFoes;
import static brewster.chess.mother.PieceMother.getSpots2;
import static brewster.chess.mother.PieceMother.getWhiteKnight;
import static org.assertj.core.api.Assertions.assertThat;

public class KnightTest {
    Piece knight;
    List<Spot> allSpots;
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
        List<Spot> legalMoves = knight.calculateLegalMoves(allSpots, foes);
        assertThat(legalMoves).containsExactlyInAnyOrder(new Spot(4,2), new Spot(3,3), new Spot(1,3));
    }

    @Test
    void calculateLegalMoves2(){
        knight.move(44);
        List<Spot> legalMoves = knight.calculateLegalMoves(allSpots, foes);
        assertThat(legalMoves).containsExactlyInAnyOrder(new Spot(3,2), new Spot(2,3), new Spot(6,3),
                new Spot(5,6), new Spot(6,5), new Spot(3,6), new Spot(2,5), new Spot(5,2));
    }

    @Test
    void calculateLegalMoves3(){
        knight.move(44);
        allSpots.add(new Spot(3,2));
        allSpots.add(new Spot(2,3));
        foes.add(new Pawn(Team.BLACK, 2, 3));
        List<Spot> legalMoves = knight.calculateLegalMoves(allSpots, foes);
        assertThat(legalMoves).containsExactlyInAnyOrder(new Spot(2,3), new Spot(6,3),
                new Spot(5,6), new Spot(6,5), new Spot(3,6), new Spot(2,5), new Spot(5,2));
    }

    @Test
    void isLegalAttack() {
        knight.move(42);
        assertThat(knight.isLegalAttack(new Spot(6, 3), allSpots)).isTrue();
        assertThat(knight.isLegalAttack(new Spot(5, 4), allSpots)).isTrue();
        assertThat(knight.isLegalAttack(new Spot(6, 4), allSpots)).isFalse();
        assertThat(knight.isLegalAttack(new Spot(7, 2), allSpots)).isFalse();
        assertThat(knight.isLegalAttack(new Spot(2, 1), allSpots)).isTrue();
        assertThat(knight.isLegalAttack(new Spot(3, 4), allSpots)).isTrue();
        assertThat(knight.isLegalAttack(new Spot(5, 3), allSpots)).isFalse();
        assertThat(knight.isLegalAttack(new Spot(7, 5), allSpots)).isFalse();
    }
}
