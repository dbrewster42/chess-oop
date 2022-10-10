package brewster.chess.piece;

import brewster.chess.model.piece.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import brewster.chess.model.piece.Spot;
import java.util.List;
import java.util.stream.Collectors;

import static brewster.chess.mother.PieceMother.getEnterprisingBlackPawn;
import static brewster.chess.mother.PieceMother.getFoes;
import static brewster.chess.mother.PieceMother.getSpots2;
import static brewster.chess.mother.PieceMother.getWhiteQueen;
import static brewster.chess.util.TeamCreation.getNewTeam;
import static org.assertj.core.api.Assertions.assertThat;

class QueenTest {

    Piece queen;
    List<Spot> allSpots;
    List<Piece> foes;


    @BeforeEach
    void setup(){
        queen = getWhiteQueen();
        allSpots = getSpots2();
        foes = getFoes();
    }
    @Test
    void calculatePotentialMoves() {
        List<Spot> legalMoves = queen.calculateLegalMoves(allSpots, foes);
        assertThat(legalMoves.size()).isEqualTo(17);
    }

    @Test
    void calculatePotentialMoves2() {
        allSpots.add(new Spot(4, 2));
        foes.add(getEnterprisingBlackPawn());
        List<Spot> legalMoves = queen.calculateLegalMoves(allSpots, foes);
        assertThat(legalMoves.size()).isEqualTo(11);
    }

    @Test
    void calculatePotentialMovesEmptyBoard() {
        List<Spot> legalMoves = queen.calculateLegalMoves(List.of(new Spot(4, 1)), List.of());
        assertThat(legalMoves.size()).isEqualTo(21);

        queen.move(54);
        legalMoves = queen.calculateLegalMoves(List.of(new Spot(5, 4)), List.of());
        assertThat(legalMoves.size()).isEqualTo(27);
    }

    @Test
    void calculatePotentialMovesFullBoard() {
        List<Piece> fullTeam = getNewTeam(true);
        List<Spot> spots = fullTeam.stream().map(Piece::getSpot).collect(Collectors.toList());
        List<Spot> legalMoves = queen.calculateLegalMoves(spots, List.of());
        assertThat(legalMoves.size()).isEqualTo(0);

        legalMoves = queen.calculateLegalMoves(spots, fullTeam);
        assertThat(legalMoves.size()).isEqualTo(5);
    }

    @Test
    void isLegalAttackDiagonal() {
        queen.move(42);
        assertThat(queen.isLegalAttack(new Spot(5, 3), allSpots)).isTrue();
        assertThat(queen.isLegalAttack(new Spot(6, 4), allSpots)).isTrue();
        assertThat(queen.isLegalAttack(new Spot(3, 1), allSpots)).isTrue();
        assertThat(queen.isLegalAttack(new Spot(2, 4), allSpots)).isTrue();
        assertThat(queen.isLegalAttack(new Spot(3, 4), allSpots)).isFalse();
        assertThat(queen.isLegalAttack(new Spot(8, 8), allSpots)).isFalse();
    }

    @Test
    void isLegalAttackStraight() {
        queen.move(42);
        assertThat(queen.isLegalAttack(new Spot(8, 2), allSpots)).isTrue();
        assertThat(queen.isLegalAttack(new Spot(1, 2), allSpots)).isTrue();
        assertThat(queen.isLegalAttack(new Spot(4, 8), allSpots)).isTrue();
        assertThat(queen.isLegalAttack(new Spot(4, 1), allSpots)).isTrue();
        assertThat(queen.isLegalAttack(new Spot(5, 4), allSpots)).isFalse();
        assertThat(queen.isLegalAttack(new Spot(3, 9), allSpots)).isFalse();
    }

    @Test
    void isLegalAttackBlocked() {
        queen.move(42);
        allSpots.add(new Spot(7, 2));
        assertThat(queen.isLegalAttack(new Spot(8, 2), allSpots)).isFalse();
        allSpots.add(new Spot(1, 2));
        assertThat(queen.isLegalAttack(new Spot(1, 2), allSpots)).isTrue();
        allSpots.add(new Spot(5, 3));
        assertThat(queen.isLegalAttack(new Spot(6, 4), allSpots)).isFalse();
        allSpots.add(new Spot(3, 3));
        assertThat(queen.isLegalAttack(new Spot(1, 5), allSpots)).isFalse();
    }
}