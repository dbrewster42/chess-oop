package brewster.chess.piece;

import org.junit.jupiter.api.Test;

import java.util.List;

import static brewster.chess.mother.PieceMother.getBlackKing;
import static brewster.chess.mother.PieceMother.getBlackPawn;
import static brewster.chess.mother.PieceMother.getWhiteKing;
import static brewster.chess.mother.PieceMother.getWhitePawn;
import static org.assertj.core.api.Assertions.assertThat;

class KingTest {
    Piece king = getWhiteKing();
    List<Piece> allPieces = getPieces();

    @Test
    void calculatePotentialMoves() {
        king.move(41);
        assertThat(king.calculatePotentialMoves(allPieces).size()).isEqualTo(5);
        king.move(42);
        assertThat(king.calculatePotentialMoves(allPieces).size()).isEqualTo(8);
    }

    @Test
    void calculatePotentialMovesWithPieces() {
        king.move(81);
        assertThat(king.calculatePotentialMoves(allPieces).size()).isEqualTo(2);
        king.move(88);
        assertThat(king.calculatePotentialMoves(allPieces).size()).isEqualTo(3);
    }


    private List<Piece> getPieces(){
        return List.of(king, getBlackKing(), getBlackPawn(), getWhitePawn());
    }
}