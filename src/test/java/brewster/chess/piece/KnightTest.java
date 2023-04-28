package brewster.chess.piece;

import brewster.chess.model.constant.Team;
import brewster.chess.model.piece.Pawn;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static brewster.chess.mother.PieceMother.getFoes;
import static brewster.chess.mother.PieceMother.getSpots2;
import static brewster.chess.mother.PieceMother.getWhiteKnight;
import static org.assertj.core.api.Assertions.assertThat;

public class KnightTest {
    Piece knight;
    List<Square> allSquares;
    List<Piece> foes;


    @BeforeEach
    void setup(){
        knight = getWhiteKnight();
        allSquares = getSpots2();
        foes = getFoes();
    }

    @Test
    void calculateLegalMoves(){
        knight.move(21);
        List<Square> legalMoves = knight.calculateLegalMoves(allSquares, foes);
        assertThat(legalMoves).containsExactlyInAnyOrder(new Square(4,2), new Square(3,3), new Square(1,3));
    }

    @Test
    void calculateLegalMoves2(){
        knight.move(44);
        List<Square> legalMoves = knight.calculateLegalMoves(allSquares, foes);
        assertThat(legalMoves).containsExactlyInAnyOrder(new Square(3,2), new Square(2,3), new Square(6,3),
                new Square(5,6), new Square(6,5), new Square(3,6), new Square(2,5), new Square(5,2));
    }

    @Test
    void calculateLegalMoves3(){
        knight.move(44);
        allSquares.add(new Square(3,2));
        allSquares.add(new Square(2,3));
        foes.add(new Pawn(Team.BLACK, 2, 3));
        List<Square> legalMoves = knight.calculateLegalMoves(allSquares, foes);
        assertThat(legalMoves).containsExactlyInAnyOrder(new Square(2,3), new Square(6,3),
                new Square(5,6), new Square(6,5), new Square(3,6), new Square(2,5), new Square(5,2));
    }

    @Test
    void isLegalAttack() {
        knight.move(42);
        assertThat(knight.isLegalAttack(new Square(6, 3), allSquares)).isTrue();
        assertThat(knight.isLegalAttack(new Square(5, 4), allSquares)).isTrue();
        assertThat(knight.isLegalAttack(new Square(6, 4), allSquares)).isFalse();
        assertThat(knight.isLegalAttack(new Square(7, 2), allSquares)).isFalse();
        assertThat(knight.isLegalAttack(new Square(2, 1), allSquares)).isTrue();
        assertThat(knight.isLegalAttack(new Square(3, 4), allSquares)).isTrue();
        assertThat(knight.isLegalAttack(new Square(5, 3), allSquares)).isFalse();
        assertThat(knight.isLegalAttack(new Square(7, 5), allSquares)).isFalse();
    }
}
