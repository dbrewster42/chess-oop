package brewster.chess.piece;

import brewster.chess.model.constant.Team;
import brewster.chess.model.constant.Type;
import lombok.Data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static brewster.chess.model.constant.Team.BLACK;
import static brewster.chess.model.constant.Team.WHITE;
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

    private int selectY(){
        int y = 1;
        if (type.equals(PAWN)){
            y = 2;
        }
        if (team.equals(BLACK)){
            y = 9 - y;
        }
        return y;
    }

    public abstract List<Point> calculatePotentialMoves(List<Piece> allPieces);

    public boolean isOccupied(int x, int y, List<Piece> pieces){
        return pieces.stream().anyMatch(piece -> piece.isAtPosition(x, y));
    }
    public boolean isTeammate(Team team, List<Piece> allPieces, int x, int y){
        return allPieces.stream().filter(p -> p.isAtPosition(x, y)).findAny().map(Piece::getTeam).equals(Optional.of(team));
    }
    public boolean isOnBoard(int x, int y){
        return x > 0 && x < 9 && y > 0 && y < 9;
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

    List<Point> addDiagonalMoves(List<Piece> allPieces){
        List<Point> moves = new ArrayList<>();
        addMovesAlongLine(allPieces, moves, -1, -1);
        addMovesAlongLine(allPieces, moves, 1, 1);
        addMovesAlongLine(allPieces, moves, 1, -1);
        addMovesAlongLine(allPieces, moves, -1, 1);
        return moves;
    }

    List<Point> addUpAndDownMoves(List<Piece> allPieces) {
        List<Point> moves = new ArrayList<>();
        addMovesAlongLine(allPieces, moves, -1, 0);
        addMovesAlongLine(allPieces, moves, 1, 0);
        addMovesAlongLine(allPieces, moves, 0, -1);
        addMovesAlongLine(allPieces, moves, 0, 1);
        return moves;
    }
    void addMovesAlongLine(List<Piece> allPieces, List<Point> moves, int xDirection, int yDirection) {
        int x = spot.x + xDirection;
        int y = spot.y + yDirection;
        while (isOnBoard(x, y)){
            if (!isOccupied(x, y, allPieces)){
                moves.add(new Point(x, y));
            } else {
                if (!isTeammate(team, allPieces, x, y)){
                    moves.add(new Point(x, y));
                }
                break;
            }
            x += xDirection;
            y += yDirection;
        }
    }
}
