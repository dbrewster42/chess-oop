package brewster.chess.piece;

import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static brewster.chess.mother.PieceMother.getFoes;
import static brewster.chess.mother.PieceMother.getSpotsForKing;
import static brewster.chess.mother.PieceMother.getWhiteKing;
import static org.assertj.core.api.Assertions.assertThat;

class KingTest {
    Piece king;
    List<Square> allSquares;
    List<Piece> foes;

    @BeforeEach
    void setup(){
        king = getWhiteKing();
        allSquares = getSpotsForKing();
        foes = getFoes();
    }

    @Test
    void calculatePotentialMoves() {
        moveKing(41);
        assertThat(king.calculateLegalMoves(allSquares, foes).size()).isEqualTo(5);
    }
    @Test
    void calculatePotentialMoves2() {
        moveKing(42);
        assertThat(king.calculateLegalMoves(allSquares, foes).size()).isEqualTo(8);
    }


    @Test
    void calculatePotentialMovesWithPieces() {
        moveKing(81);
        assertThat(king.calculateLegalMoves(allSquares, foes).size()).isEqualTo(2);
        moveKing(88);
        assertThat(king.calculateLegalMoves(allSquares, foes).size()).isEqualTo(3);
    }

    @Test
    void isLegalAttack() {
        moveKing(42);
        assertThat(king.isLegalAttack(new Square(5, 3), allSquares)).isTrue();
        assertThat(king.isLegalAttack(new Square(5, 2), allSquares)).isTrue();
        assertThat(king.isLegalAttack(new Square(6, 3), allSquares)).isFalse();
        assertThat(king.isLegalAttack(new Square(6, 2), allSquares)).isFalse();
        assertThat(king.isLegalAttack(new Square(4, 3), allSquares)).isTrue();
        assertThat(king.isLegalAttack(new Square(3, 3), allSquares)).isTrue();
        assertThat(king.isLegalAttack(new Square(3, 1), allSquares)).isTrue();
        assertThat(king.isLegalAttack(new Square(1, 1), allSquares)).isFalse();
        assertThat(king.isLegalAttack(new Square(2, 3), allSquares)).isFalse();
    }

    private void moveKing(int position){
        king.move(position);
        allSquares.add(new Square(position / 10, position % 10));
    }
}