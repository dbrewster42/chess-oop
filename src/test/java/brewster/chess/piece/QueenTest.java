package brewster.chess.piece;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
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
    List<Point> allSpots;
    List<Piece> foes;


    @BeforeEach
    void setup(){
        queen = getWhiteQueen();
        allSpots = getSpots2();
        foes = getFoes();
    }
    @Test
    void calculatePotentialMoves() {
        List<Point> legalMoves = queen.calculateLegalMoves(allSpots, foes);
        assertThat(legalMoves.size()).isEqualTo(17);
    }

    @Test
    void calculatePotentialMoves2() {
        allSpots.add(new Point(4, 2));
        foes.add(getEnterprisingBlackPawn());
        List<Point> legalMoves = queen.calculateLegalMoves(allSpots, foes);
        assertThat(legalMoves.size()).isEqualTo(11);
    }

    @Test
    void calculatePotentialMovesEmptyBoard() {
        List<Point> legalMoves = queen.calculateLegalMoves(List.of(new Point(4, 1)), List.of());
        assertThat(legalMoves.size()).isEqualTo(21);

        queen.move(54);
        legalMoves = queen.calculateLegalMoves(List.of(new Point(5, 4)), List.of());
        assertThat(legalMoves.size()).isEqualTo(27);
    }

    @Test
    void calculatePotentialMovesFullBoard() {
        List<Piece> fullTeam = getNewTeam(true);
        List<Point> spots = fullTeam.stream().map(Piece::getSpot).collect(Collectors.toList());
        List<Point> legalMoves = queen.calculateLegalMoves(spots, List.of());
        assertThat(legalMoves.size()).isEqualTo(0);

        legalMoves = queen.calculateLegalMoves(spots, fullTeam);
        assertThat(legalMoves.size()).isEqualTo(5);
    }

}