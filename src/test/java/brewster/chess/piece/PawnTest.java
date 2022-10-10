package brewster.chess.piece;

import brewster.chess.exception.Promotion;
import brewster.chess.model.constant.Team;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import brewster.chess.model.piece.Spot;
import java.util.List;

import static brewster.chess.mother.PieceMother.getFoes;
import static brewster.chess.mother.PieceMother.getSpots2;
import static brewster.chess.mother.PieceMother.getWhitePawn;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PawnTest {
    Piece pawn;
    List<Spot> allSpots;
    List<Piece> foes;


    @BeforeEach
    void setup(){
        pawn = getWhitePawn();
        allSpots = getSpots2();
        foes = getFoes();
    }
    @Test
    void calculatePotentialMoves() {
        List<Spot> legalMoves = pawn.calculateLegalMoves(allSpots, foes);
        assertThat(legalMoves.size()).isEqualTo(2);

        pawn.move(83);
        assertThat(pawn.calculateLegalMoves(allSpots, foes)).containsExactly(new Spot(8, 4));
    }

    @Test
    void calculatePotentialMoves2() {
        pawn.move(83);
        allSpots.add(new Spot(7, 4));
        assertThat(pawn.calculateLegalMoves(allSpots, foes)).containsExactly(new Spot(8, 4));
    }

    @Test
    void calculatePotentialMoves3() {
        pawn.move(83);
        allSpots.add(new Spot(7, 4));
        foes.add(new Rook(Team.BLACK, 7, 4));
        List<Spot> legalMoves = pawn.calculateLegalMoves(allSpots, foes);
        assertThat(legalMoves.size()).isEqualTo(2);
        assertThat(legalMoves).containsExactlyInAnyOrder(new Spot(7, 4), new Spot(8, 4));
    }

    @Test
    void promotion() {
        assertThrows(Promotion.class, () -> pawn.move(78));
        assertThrows(Promotion.class, () -> pawn.move(51));
    }

    @Test
    void isLegalAttack() {
        pawn.move(42);
        assertThat(pawn.isLegalAttack(new Spot(3, 3), allSpots)).isTrue();
        assertThat(pawn.isLegalAttack(new Spot(5, 3), allSpots)).isTrue();
        assertThat(pawn.isLegalAttack(new Spot(6, 3), allSpots)).isFalse();
        assertThat(pawn.isLegalAttack(new Spot(6, 2), allSpots)).isFalse();
        assertThat(pawn.isLegalAttack(new Spot(5, 2), allSpots)).isFalse();
        assertThat(pawn.isLegalAttack(new Spot(4, 3), allSpots)).isFalse();
        assertThat(pawn.isLegalAttack(new Spot(3, 1), allSpots)).isFalse();
        assertThat(pawn.isLegalAttack(new Spot(1, 1), allSpots)).isFalse();
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