package brewster.chess.piece;

import brewster.chess.model.constant.Team;
import brewster.chess.model.constant.Type;
import lombok.Data;

import java.awt.Point;
import java.util.List;

@Data
//@AllArgsConstructor
public abstract class Piece {
    Type type;
    Point spot;
    Team team;


    public Piece(Type type, int x, int y) {
        this.type = type;
        this.spot = new Point(x, y);
    }

    public int getLocation(){
        return spot.x * 10 + spot.y;
    }
//    Type getType();
//    String getTeam();
    public abstract List<Point> calculatePotentialMoves(List<Point> friends, List<Point> foes);
    public void makeMove(int newPosition){
        spot.move(newPosition / 10, newPosition % 10);
//        this.spot.x = newPosition / 10;
//        this.spot.y = newPosition % 10;
    }
    public void makeMove(int x, int y){
        spot.move(x, y);
//        this.spot.x = newPosition / 10;
//        this.spot.y = newPosition % 10;
    }

//    public static int convertToPosition(){
//        return 1;
//    }
    public boolean isAtPosition(int position){
        return spot.x == position / 10 && spot.y == position % 10;
    }

}
