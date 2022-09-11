package brewster.chess.piece;

import brewster.chess.model.constant.Team;
import brewster.chess.model.constant.Type;
import lombok.Data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import static brewster.chess.model.constant.Team.BLACK;
import static brewster.chess.model.constant.Type.PAWN;

@Data
public abstract class Piece {
    Team team;
    Type type;
    Point spot;

    public Piece(Team team, int x, int y, Type type) {
        this.team = team;
        this.type = type;
        this.spot = new Point(x, y);
    }

    public Piece(Team team, int x, Type type) {
        this.team = team;
        this.type = type;
        this.spot = new Point(x, selectY());
    }

    public abstract List<Point> calculateLegalMoves(List<Point> allSpots, List<Piece> foes);

    public abstract boolean isLegalAttack(Point destination, List<Point> allSpots);


    private int selectY() {
        int y = 1;
        if (type.equals(PAWN)) {
            y = 2;
        }
        if (team.equals(BLACK)) {
            y = 9 - y;
        }
        return y;
    }

    public boolean isOccupied(int x, int y, List<Point> spots) {
        return spots.contains(new Point(x, y));
//        return pieces.stream().anyMatch(piece -> piece.isAtPosition(x, y));
    }

    public boolean isOpponent(List<Piece> foes, int x, int y) {
        return foes.stream().anyMatch(p -> p.isAtPosition(x, y));
    }

    public boolean isOnBoard(int x, int y) {
        return x > 0 && x < 9 && y > 0 && y < 9;
    }

    public int getLocation() {
        return spot.x * 10 + spot.y;
    }

    public void move(int newPosition) {
        spot.move(newPosition / 10, newPosition % 10);
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

    public Team getTeam() {
        return team;
    }

    public Type getType() {
        return type;
    }

    public Point getSpot() {
        return spot;
    }

    List<Point> addDiagonalMoves(List<Point> allSpots, List<Piece> foes) {
        List<Point> moves = new ArrayList<>();
        addMovesAlongLine(moves, allSpots, foes, -1, -1);
        addMovesAlongLine(moves, allSpots, foes, 1, 1);
        addMovesAlongLine(moves, allSpots, foes, 1, -1);
        addMovesAlongLine(moves, allSpots, foes, -1, 1);
        return moves;
    }

//    List<Point> addUpAndDownMoves(List<Point> allSpots, List<Piece> foes) {
//        List<Point> moves = new ArrayList<>();
//        addMovesAlongLine(moves, allSpots, foes, -1, 0);
//        addMovesAlongLine(moves, allSpots, foes, 1, 0);
//        addMovesAlongLine(moves, allSpots, foes, 0, -1);
//        addMovesAlongLine(moves, allSpots, foes, 0, 1);
//        return moves;
//    }
    List<Point> addUpAndDownMoves(List<Point> allSpots, List<Piece> foes) {
        List<Point> moves = addMovesAlongLine(new ArrayList<>(), allSpots, foes, -1, 0);
        addMovesAlongLine(moves, allSpots, foes, 1, 0);
        addMovesAlongLine(moves, allSpots, foes, 0, -1);
        return addMovesAlongLine(moves, allSpots, foes, 0, 1);
    }

    public List<Point> addMovesAlongLine(List<Point> moves, List<Point> allSpots, List<Piece> foes, int xDirection, int yDirection) {
        int x = spot.x + xDirection;
        int y = spot.y + yDirection;
        while (isOnBoard(x, y)) {
            if (!isOccupied(x, y, allSpots)) {
                moves.add(new Point(x, y));
            } else {
                if (isOpponent(foes, x, y)) {
                    moves.add(new Point(x, y));
                }
                break;
            }
            x += xDirection;
            y += yDirection;
        }
        return moves;
    }

    private boolean isMoveUnblocked(Point destination, List<Point> allSpots, int xDirection, int yDirection) {
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
        throw new RuntimeException("isMoveUnblocked() should not have been called because point cannot be reached");
    }

    boolean isOnDiagonalLine(Point destination, List<Point> allSpots) {
        if (Math.abs(destination.x - spot.x) == Math.abs(destination.y - spot.y)) {
            int xDirection = destination.x - spot.x > 0 ? 1 : -1;
            int yDirection = destination.y - spot.y > 0 ? 1 : -1;
            return isMoveUnblocked(destination, allSpots, xDirection, yDirection);
        }
        return false;
    }

    boolean isOnStraightLine(Point destination, List<Point> allSpots) {
        if (destination.x - spot.x == 0) {
            return isMoveUnblocked(destination, allSpots, 0, destination.y - spot.y > 0 ? 1 : -1);
        } else if (destination.y - spot.y == 0) {
            return isMoveUnblocked(destination, allSpots, destination.x - spot.x > 0 ? 1 : -1, 0);
        }
        return false;
    }
}