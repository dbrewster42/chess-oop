package brewster.chess.piece;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.List;

import static brewster.chess.mother.PieceMother.getFoes;
import static brewster.chess.mother.PieceMother.getSpotsForKing;
import static brewster.chess.mother.PieceMother.getWhiteKing;
import static org.assertj.core.api.Assertions.assertThat;

class KingTest {
    Piece king;
    List<Point> allSpots;
    List<Piece> foes;

    @BeforeEach
    void setup(){
        king = getWhiteKing();
        allSpots = getSpotsForKing();
        foes = getFoes();
    }

    @Test
    void calculatePotentialMoves() {
        moveKing(41);
        assertThat(king.calculateLegalMoves(allSpots, foes).size()).isEqualTo(5);
    }
    @Test
    void calculatePotentialMoves2() {
        moveKing(42);
        assertThat(king.calculateLegalMoves(allSpots, foes).size()).isEqualTo(8);
    }


    @Test
    void calculatePotentialMovesWithPieces() {
        moveKing(81);
        assertThat(king.calculateLegalMoves(allSpots, foes).size()).isEqualTo(2);
        moveKing(88);
        assertThat(king.calculateLegalMoves(allSpots, foes).size()).isEqualTo(3);
    }

    @Test
    void isLegalAttack() {
        moveKing(42);
        assertThat(king.isLegalAttack(new Point(5, 3), allSpots)).isTrue();
        assertThat(king.isLegalAttack(new Point(5, 2), allSpots)).isTrue();
        assertThat(king.isLegalAttack(new Point(6, 3), allSpots)).isFalse();
        assertThat(king.isLegalAttack(new Point(6, 2), allSpots)).isFalse();
        assertThat(king.isLegalAttack(new Point(4, 3), allSpots)).isTrue();
        assertThat(king.isLegalAttack(new Point(3, 3), allSpots)).isTrue();
        assertThat(king.isLegalAttack(new Point(3, 1), allSpots)).isTrue();
        assertThat(king.isLegalAttack(new Point(1, 1), allSpots)).isFalse();
        assertThat(king.isLegalAttack(new Point(2, 3), allSpots)).isFalse();
    }

    private void moveKing(int position){
        king.move(position);
        allSpots.add(new Point(position / 10, position % 10));
    }
}