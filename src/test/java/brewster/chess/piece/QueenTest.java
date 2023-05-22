package brewster.chess.piece;

import brewster.chess.model.constant.Team;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static brewster.chess.mother.PieceMother.getEnterprisingBlackPawn;
import static brewster.chess.mother.PieceMother.getFoes;
import static brewster.chess.mother.PieceMother.getSpots2;
import static brewster.chess.mother.PieceMother.getWhiteQueen;
import static brewster.chess.util.TeamCreation.generatePieces;
import static org.assertj.core.api.Assertions.assertThat;

class QueenTest {

    Piece queen;
    List<Square> allSquares;
    List<Piece> foes;


    @BeforeEach
    void setup(){
        queen = getWhiteQueen();
        allSquares = getSpots2();
        foes = getFoes();
    }
    @Test
    void calculatePotentialMoves() {
        List<Square> legalMoves = queen.calculateLegalMoves(allSquares, foes);
        assertThat(legalMoves.size()).isEqualTo(17);
    }

    @Test
    void calculatePotentialMoves2() {
        allSquares.add(new Square(4, 2));
        foes.add(getEnterprisingBlackPawn());
        List<Square> legalMoves = queen.calculateLegalMoves(allSquares, foes);
        assertThat(legalMoves.size()).isEqualTo(11);
    }

    @Test
    void calculatePotentialMovesEmptyBoard() {
        List<Square> legalMoves = queen.calculateLegalMoves(List.of(new Square(4, 1)), List.of());
        assertThat(legalMoves.size()).isEqualTo(21);

        queen.move(54);
        legalMoves = queen.calculateLegalMoves(List.of(new Square(5, 4)), List.of());
        assertThat(legalMoves.size()).isEqualTo(27);
    }

    @Test
    void calculatePotentialMovesFullBoard() {
        List<Piece> fullTeam = generatePieces(Team.WHITE);
        List<Square> squares = fullTeam.stream().map(Piece::getSquare).collect(Collectors.toList());
        List<Square> legalMoves = queen.calculateLegalMoves(squares, List.of());
        assertThat(legalMoves.size()).isEqualTo(0);

        legalMoves = queen.calculateLegalMoves(squares, fullTeam);
        assertThat(legalMoves.size()).isEqualTo(5);
    }

    @Test
    void isLegalAttackDiagonal() {
        queen.move(42);
        assertThat(queen.isLegalAttack(new Square(5, 3), allSquares)).isTrue();
        assertThat(queen.isLegalAttack(new Square(6, 4), allSquares)).isTrue();
        assertThat(queen.isLegalAttack(new Square(3, 1), allSquares)).isTrue();
        assertThat(queen.isLegalAttack(new Square(2, 4), allSquares)).isTrue();
        assertThat(queen.isLegalAttack(new Square(3, 4), allSquares)).isFalse();
        assertThat(queen.isLegalAttack(new Square(8, 8), allSquares)).isFalse();
    }

    @Test
    void isLegalAttackStraight() {
        queen.move(42);
        assertThat(queen.isLegalAttack(new Square(8, 2), allSquares)).isTrue();
        assertThat(queen.isLegalAttack(new Square(1, 2), allSquares)).isTrue();
        assertThat(queen.isLegalAttack(new Square(4, 8), allSquares)).isTrue();
        assertThat(queen.isLegalAttack(new Square(4, 1), allSquares)).isTrue();
        assertThat(queen.isLegalAttack(new Square(5, 4), allSquares)).isFalse();
        assertThat(queen.isLegalAttack(new Square(3, 9), allSquares)).isFalse();
    }

    @Test
    void isLegalAttackBlocked() {
        queen.move(42);
        allSquares.add(new Square(7, 2));
        assertThat(queen.isLegalAttack(new Square(8, 2), allSquares)).isFalse();
        allSquares.add(new Square(1, 2));
        assertThat(queen.isLegalAttack(new Square(1, 2), allSquares)).isTrue();
        allSquares.add(new Square(5, 3));
        assertThat(queen.isLegalAttack(new Square(6, 4), allSquares)).isFalse();
        allSquares.add(new Square(3, 3));
        assertThat(queen.isLegalAttack(new Square(1, 5), allSquares)).isFalse();
    }
}