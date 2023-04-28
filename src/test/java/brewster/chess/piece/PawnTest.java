package brewster.chess.piece;

import brewster.chess.exception.Promotion;
import brewster.chess.model.constant.Team;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.Rook;
import brewster.chess.model.piece.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static brewster.chess.mother.PieceMother.getFoes;
import static brewster.chess.mother.PieceMother.getSpots2;
import static brewster.chess.mother.PieceMother.getWhitePawn;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PawnTest {
    Piece pawn;
    List<Square> allSquares;
    List<Piece> foes;


    @BeforeEach
    void setup(){
        pawn = getWhitePawn();
        allSquares = getSpots2();
        foes = getFoes();
    }
    @Test
    void calculatePotentialMoves() {
        List<Square> legalMoves = pawn.calculateLegalMoves(allSquares, foes);
        assertThat(legalMoves.size()).isEqualTo(2);

        pawn.move(83);
        assertThat(pawn.calculateLegalMoves(allSquares, foes)).containsExactly(new Square(8, 4));
    }

    @Test
    void calculatePotentialMoves2() {
        pawn.move(83);
        allSquares.add(new Square(7, 4));
        assertThat(pawn.calculateLegalMoves(allSquares, foes)).containsExactly(new Square(8, 4));
    }

    @Test
    void calculatePotentialMoves3() {
        pawn.move(83);
        allSquares.add(new Square(7, 4));
        foes.add(new Rook(Team.BLACK, 7, 4));
        List<Square> legalMoves = pawn.calculateLegalMoves(allSquares, foes);
        assertThat(legalMoves.size()).isEqualTo(2);
        assertThat(legalMoves).containsExactlyInAnyOrder(new Square(7, 4), new Square(8, 4));
    }

    @Test
    void promotion() {
        assertThrows(Promotion.class, () -> pawn.move(78));
        assertThrows(Promotion.class, () -> pawn.move(51));
    }

    @Test
    void isLegalAttack() {
        pawn.move(42);
        assertThat(pawn.isLegalAttack(new Square(3, 3), allSquares)).isTrue();
        assertThat(pawn.isLegalAttack(new Square(5, 3), allSquares)).isTrue();
        assertThat(pawn.isLegalAttack(new Square(6, 3), allSquares)).isFalse();
        assertThat(pawn.isLegalAttack(new Square(6, 2), allSquares)).isFalse();
        assertThat(pawn.isLegalAttack(new Square(5, 2), allSquares)).isFalse();
        assertThat(pawn.isLegalAttack(new Square(4, 3), allSquares)).isFalse();
        assertThat(pawn.isLegalAttack(new Square(3, 1), allSquares)).isFalse();
        assertThat(pawn.isLegalAttack(new Square(1, 1), allSquares)).isFalse();
    }

//    @Test
//    void hasMoved() {
//        Pawn pawn = new Pawn(Team.WHITE, 1, 2);
//        assertThat(pawn.hasNotMoved()).isTrue();
//        pawn.move(13);
//        assertThat(pawn.hasNotMoved()).isFalse();
//        pawn.move(17);
//        assertThat(pawn.hasNotMoved()).isFalse();
//    }
//
//    @Test
//    void hasMoved2() {
//        Pawn pawn = new Pawn(Team.BLACK, 1, 2);
//        assertThat(pawn.hasNotMoved()).isFalse();
//        pawn.move(13);
//        assertThat(pawn.hasNotMoved()).isFalse();
//        pawn.move(17);
//        assertThat(pawn.hasNotMoved()).isTrue();
//    }
}