package brewster.chess.piece;

import brewster.chess.exception.Promotion;
import brewster.chess.model.constant.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.List;

import static brewster.chess.mother.PieceMother.getFoes;
import static brewster.chess.mother.PieceMother.getSpots2;
import static brewster.chess.mother.PieceMother.getWhitePawn;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PawnTest {
    Piece pawn;
    List<Point> allSpots;
    List<Piece> foes;


    @BeforeEach
    void setup(){
        pawn = getWhitePawn();
        allSpots = getSpots2();
        foes = getFoes();
    }
    @Test
    void calculatePotentialMoves() {
        List<Point> legalMoves = pawn.calculateLegalMoves(allSpots, foes);
        assertThat(legalMoves.size()).isEqualTo(2);

        pawn.move(83);
        assertThat(pawn.calculateLegalMoves(allSpots, foes)).containsExactly(new Point(8, 4));
    }

    @Test
    void calculatePotentialMoves2() {
        pawn.move(83);
        allSpots.add(new Point(7, 4));
        assertThat(pawn.calculateLegalMoves(allSpots, foes)).containsExactly(new Point(8, 4));
    }

    @Test
    void calculatePotentialMoves3() {
        pawn.move(83);
        allSpots.add(new Point(7, 4));
        foes.add(new Rook(Team.BLACK, 7, 4));
        List<Point> legalMoves = pawn.calculateLegalMoves(allSpots, foes);
        assertThat(legalMoves.size()).isEqualTo(2);
        assertThat(legalMoves).containsExactlyInAnyOrder(new Point(7, 4), new Point(8, 4));
    }

    @Test
    void promotion() {
        assertThrows(Promotion.class, () -> pawn.move(78));
        assertThrows(Promotion.class, () -> pawn.move(51));
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