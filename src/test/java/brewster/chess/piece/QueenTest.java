package brewster.chess.piece;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.List;

import static brewster.chess.mother.PieceMother.getEnterprisingBlackPawn;
import static brewster.chess.mother.PieceMother.getFoes;
import static brewster.chess.mother.PieceMother.getSpots2;
import static brewster.chess.mother.PieceMother.getWhiteQueen;
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

//    private void moveQueen(int x, int y){
//        queen.move(x, y);
//        allSpots.add(new Point(x, y));
//    }
}