package brewster.chess.piece;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Piece {
    Type type;
//    private boolean isWhite;
    int x;
    int y;


    public int getLocation(){
        return x * 10 + y;
    }
//    Type getType();
//    String getTeam();
    public abstract int[] calculatePotentialMoves();
    public void makeMove(int newPosition){
        this.x = newPosition / 10;
        this.y = newPosition % 10;
    }

//    public static int convertToPosition(){
//        return 1;
//    }
    public boolean isAtPosition(int position){
        return x == position / 10 && y == position % 10;
    }

}
