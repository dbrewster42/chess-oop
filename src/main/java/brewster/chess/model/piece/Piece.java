package brewster.chess.model.piece;

import brewster.chess.model.constant.Team;
import brewster.chess.model.constant.Type;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "Type")
@Data
@NoArgsConstructor
public abstract class Piece {
    @Id
    @GeneratedValue
    private long id;
    @NotNull Team team;
    @NotNull Type type;
    @NotNull Square square;


    public abstract List<Square> calculateLegalMoves(List<Square> allSquares, List<Piece> foes);
    public abstract boolean isLegalAttack(Square destination, List<Square> allSquares);
    public boolean isLegalBlock(Square destination, List<Square> allSquares) { return isLegalAttack(destination, allSquares); }
    public boolean isLegalMove(Square destination, List<Square> allSquares, List<Piece> foes) {
        return calculateLegalMoves(allSquares, foes).contains(destination);
    }



    Piece(Team team, int x, int y, Type type) {
        this.team = team;
        this.square = new Square(x, y);
        this.type = type;
    }

    public int getLocation() {
        return square.intValue();
    }

    public Square getSquare() {
        return square;
    }

    public Team getTeam() {
        return team;
    }

    public Type getType() {
        return type;
    }

    public void move(int newPosition) {
        square.move(newPosition);
    }

    public void move(int x, int y) {
        square.move(x, y);
    }

    public boolean isAtPosition(int position) {
        return square.x == position / 10 && square.y == position % 10;
    }

    public boolean isAtPosition(int x, int y) {
        return square.x == x && square.y == y;
    }

    public boolean isOccupied(int x, int y, List<Square> squares) {
        return squares.contains(new Square(x, y));
    }

    public boolean isOpponent(List<Piece> foes, int x, int y) {
        return foes.stream().anyMatch(p -> p.isAtPosition(x, y));
    }

    public boolean isOnBoard(int x, int y) {
        return x > 0 && x < 9 && y > 0 && y < 9;
    }

    List<Square> addDiagonalMoves(List<Square> allSquares, List<Piece> foes) {
        List<Square> moves = new ArrayList<>();
        addMovesAlongLine(moves, allSquares, foes, -1, -1);
        addMovesAlongLine(moves, allSquares, foes, 1, 1);
        addMovesAlongLine(moves, allSquares, foes, 1, -1);
        addMovesAlongLine(moves, allSquares, foes, -1, 1);
        return moves;
    }

    List<Square> addUpAndDownMoves(List<Square> allSquares, List<Piece> foes) {
        List<Square> moves = new ArrayList<>();
        addMovesAlongLine(moves, allSquares, foes, -1, 0);
        addMovesAlongLine(moves, allSquares, foes, 1, 0);
        addMovesAlongLine(moves, allSquares, foes, 0, -1);
        addMovesAlongLine(moves, allSquares, foes, 0, 1);
        return moves;
    }

    void addMovesAlongLine(List<Square> moves, List<Square> allSquares, List<Piece> foes, int xDirection, int yDirection) {
        addMovesAlongLine(moves, allSquares, foes, xDirection, yDirection, true);
    }

    public List<Square> addMovesAlongLine(List<Square> moves, List<Square> allSquares, List<Piece> foes, int xDirection, int yDirection, boolean includeCapture) {
        int x = square.x + xDirection;
        int y = square.y + yDirection;
        while (isOnBoard(x, y)) {
            if (!isOccupied(x, y, allSquares)) {
                moves.add(new Square(x, y));
            } else {
                if (includeCapture && isOpponent(foes, x, y)) {
                    moves.add(new Square(x, y));
                }
                break;
            }
            x += xDirection;
            y += yDirection;
        }
        return moves;
    }

    private boolean isMoveUnblocked(Square destination, List<Square> allSquares, int xDirection, int yDirection) {
        if (destination.equals(square)) return false;
        int x = square.x + xDirection;
        int y = square.y + yDirection;
        while (isOnBoard(x, y)) {
            if (x == destination.x && y == destination.y) {
                return true;
            } else if (isOccupied(x, y, allSquares)) {
                return false;
            }
            x += xDirection;
            y += yDirection;
        }
        throw new RuntimeException("isMoveUnblocked() should not have been called because Square cannot be reached");
    }

    boolean isOnDiagonalLine(Square destination, List<Square> allSquares) {
        if (Math.abs(destination.x - square.x) == Math.abs(destination.y - square.y)) {
            int xDirection = destination.x - square.x > 0 ? 1 : -1;
            int yDirection = destination.y - square.y > 0 ? 1 : -1;
            return isMoveUnblocked(destination, allSquares, xDirection, yDirection);
        }
        return false;
    }

    boolean isOnStraightLine(Square destination, List<Square> allSquares) {
        if (destination.x - square.x == 0) {
            return isMoveUnblocked(destination, allSquares, 0, destination.y - square.y > 0 ? 1 : -1);
        } else if (destination.y - square.y == 0) {
            return isMoveUnblocked(destination, allSquares, destination.x - square.x > 0 ? 1 : -1, 0);
        }
        return false;
    }

}