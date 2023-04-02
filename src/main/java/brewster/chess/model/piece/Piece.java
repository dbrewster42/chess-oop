package brewster.chess.model.piece;

import brewster.chess.model.constant.Team;
import brewster.chess.model.constant.Type;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
//@DiscriminatorColumn(name = "Type")
public abstract class Piece {
    @Id
    @GeneratedValue
    private long id;
    @NotNull Team team;
    @NotNull Type type;
    @NotNull Spot spot;


    public abstract List<Spot> calculateLegalMoves(List<Spot> allSpots, List<Piece> foes);
    public abstract boolean isLegalAttack(Spot destination, List<Spot> allSpots);

    public Piece(Team team, int x, int y, Type type) {
        this.team = team;
        this.type = type;
        this.spot = new Spot(x, y);
    }

    public boolean isOccupied(int x, int y, List<Spot> spots) {
        return spots.contains(new Spot(x, y));
    }

    public boolean isOpponent(List<Piece> foes, int x, int y) {
        return foes.stream().anyMatch(p -> p.isAtPosition(x, y));
    }

    public boolean isOnBoard(int x, int y) {
        return x > 0 && x < 9 && y > 0 && y < 9;
    }

    public void move(int newPosition) {
        spot.move(newPosition);
    }

    public void move(int x, int y) {
        spot.move(x, y);
    }

    public boolean isAtPosition(int position) {
        return spot.x == position / 10 && spot.y == position % 10;
    }

    public boolean isAtPosition(int x, int y) {
        return spot.x == x && spot.y == y;
    }

    public int getLocation() {
        return spot.convertToInt();
    }

    public Spot getSpot() {
        return spot;
    }

    public Team getTeam() {
        return team;
    }

    public Type getType() {
        return type;
    }

    List<Spot> addDiagonalMoves(List<Spot> allSpots, List<Piece> foes) {
        List<Spot> moves = new ArrayList<>();
        addMovesAlongLine(moves, allSpots, foes, -1, -1);
        addMovesAlongLine(moves, allSpots, foes, 1, 1);
        addMovesAlongLine(moves, allSpots, foes, 1, -1);
        addMovesAlongLine(moves, allSpots, foes, -1, 1);
        return moves;
    }

    List<Spot> addUpAndDownMoves(List<Spot> allSpots, List<Piece> foes) {
        List<Spot> moves = new ArrayList<>();
        addMovesAlongLine(moves, allSpots, foes, -1, 0);
        addMovesAlongLine(moves, allSpots, foes, 1, 0);
        addMovesAlongLine(moves, allSpots, foes, 0, -1);
        addMovesAlongLine(moves, allSpots, foes, 0, 1);
        return moves;
    }

    public List<Spot> addMovesAlongLine(List<Spot> moves, List<Spot> allSpots, List<Piece> foes, int xDirection, int yDirection) {
        int x = spot.x + xDirection;
        int y = spot.y + yDirection;
        while (isOnBoard(x, y)) {
            if (!isOccupied(x, y, allSpots)) {
                moves.add(new Spot(x, y));
            } else {
                if (isOpponent(foes, x, y)) {
                    moves.add(new Spot(x, y));
                }
                break;
            }
            x += xDirection;
            y += yDirection;
        }
        return moves;
    }

    private boolean isMoveUnblocked(Spot destination, List<Spot> allSpots, int xDirection, int yDirection) {
        if (destination.equals(spot)) return false;
        int x = spot.x + xDirection;
        int y = spot.y + yDirection;
        while (isOnBoard(x, y)) {
            if (x == destination.x && y == destination.y) {
                return true;
            } else if (isOccupied(x, y, allSpots)) {
                return false;
            }
            x += xDirection;
            y += yDirection;
        }
        throw new RuntimeException("isMoveUnblocked() should not have been called because Spot cannot be reached");
    }

    boolean isOnDiagonalLine(Spot destination, List<Spot> allSpots) {
        if (Math.abs(destination.x - spot.x) == Math.abs(destination.y - spot.y)) {
            int xDirection = destination.x - spot.x > 0 ? 1 : -1;
            int yDirection = destination.y - spot.y > 0 ? 1 : -1;
            return isMoveUnblocked(destination, allSpots, xDirection, yDirection);
        }
        return false;
    }

    boolean isOnStraightLine(Spot destination, List<Spot> allSpots) {
        if (destination.x - spot.x == 0) {
            return isMoveUnblocked(destination, allSpots, 0, destination.y - spot.y > 0 ? 1 : -1);
        } else if (destination.y - spot.y == 0) {
            return isMoveUnblocked(destination, allSpots, destination.x - spot.x > 0 ? 1 : -1, 0);
        }
        return false;
    }

}