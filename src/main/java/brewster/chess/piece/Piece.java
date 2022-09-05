package brewster.chess.piece;

import brewster.chess.model.constant.Team;
import brewster.chess.model.constant.Type;
import lombok.Data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public abstract List<Point> calculateLegalMoves(List<Point> allSpots, List<Piece> foes);

    public boolean isOccupied(int x, int y, List<Point> spots){
        return spots.contains(new Point(x, y));
//        return pieces.stream().anyMatch(piece -> piece.isAtPosition(x, y));
    }
    public boolean isOpponent(List<Piece> foes, int x, int y){
        return foes.stream().anyMatch(p -> p.isAtPosition(x, y));
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

    List<Point> addDiagonalMoves(List<Point> allSpots, List<Piece> foes) {
        List<Point> moves = new ArrayList<>();
        addMovesAlongLine(moves, allSpots, foes, -1, -1);
        addMovesAlongLine(moves, allSpots, foes, 1, 1);
        addMovesAlongLine(moves, allSpots, foes, 1, -1);
        addMovesAlongLine(moves, allSpots, foes, -1, 1);
        return moves;
    }

    List<Point> addUpAndDownMoves(List<Point> allSpots, List<Piece> foes)  {
        List<Point> moves = new ArrayList<>();
        addMovesAlongLine(moves, allSpots, foes,-1, 0);
        addMovesAlongLine(moves, allSpots, foes,1, 0);
        addMovesAlongLine(moves, allSpots, foes,0, -1);
        addMovesAlongLine(moves, allSpots, foes,0, 1);
        return moves;
    }
    void addMovesAlongLine(List<Point> moves, List<Point> allSpots, List<Piece> foes, int xDirection, int yDirection) {
        int x = spot.x + xDirection;
        int y = spot.y + yDirection;
        while (isOnBoard(x, y)){
            if (!isOccupied(x, y, allSpots)){
                moves.add(new Point(x, y));
            } else {
                if (isOpponent(foes, x, y)){
                    moves.add(new Point(x, y));
                }
                break;
            }
            x += xDirection;
            y += yDirection;
        }
    }
}
