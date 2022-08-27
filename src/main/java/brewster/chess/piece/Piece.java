package brewster.chess.piece;

import brewster.chess.model.constant.Team;
import brewster.chess.model.constant.Type;
import lombok.Data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Data
public abstract class Piece {
    Team team;
    Type type;
    Point spot;

    public Piece(Team team, int x, int y) {
        this.team = team;
        this.spot = new Point(x, y);
    }

    public abstract List<Point> calculatePotentialMoves(Stream<Piece> allPieces);

    public boolean isOccupied(int x, int y, Stream<Piece> pieces){
        return pieces.anyMatch(piece -> piece.isAtPosition(x, y));
    }
    public boolean isTeammate(Team team, Stream<Piece> allPieces, int x, int y){
        return allPieces.filter(p -> p.isAtPosition(x, y)).findAny().map(Piece::getTeam).equals(team);
    }

    public int getLocation(){
        return spot.x * 10 + spot.y;
    }

    public void move(int newPosition){
        spot.move(newPosition / 10, newPosition % 10);
    }
    public void move(int x, int y){
        spot.move(x, y);
    }

    public boolean isAtPosition(int position){
        return spot.x == position / 10 && spot.y == position % 10;
    }

    public boolean isAtPosition(int x, int y){
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

    List<Point> addDiagonalMoves(Stream<Piece> allPieces){
        List<Point> moves = new ArrayList<>();
        addMovesAlongLine(allPieces, moves, -1, -1);
        addMovesAlongLine(allPieces, moves, 1, 1);
        addMovesAlongLine(allPieces, moves, 1, -1);
        addMovesAlongLine(allPieces, moves, -1, 1);
        return moves;
    }

    List<Point> addUpAndDownMoves(Stream<Piece> allPieces) {
        List<Point> moves = new ArrayList<>();
        addMovesAlongLine(allPieces, moves, -1, 0);
        addMovesAlongLine(allPieces, moves, 1, 0);
        addMovesAlongLine(allPieces, moves, 0, -1);
        addMovesAlongLine(allPieces, moves, 0, 1);
        return moves;
    }
    void addMovesAlongLine(Stream<Piece> allPieces, List<Point> moves, int xDirection, int yDirection) {
        int x = spot.x;
        int y = spot.y;
        while (x > 0 && x < 9 && y > 0 && y < 9){
            x += xDirection;
            y += yDirection;
            if (!isOccupied(x, y, allPieces)){
                moves.add(new Point(x, y));
            } else {
                if (!isTeammate(team, allPieces, x, y)){
                    moves.add(new Point(x, y));
                }
                break;
            }
        }
    }
}
