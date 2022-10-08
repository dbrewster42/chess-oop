package brewster.chess.piece;

import lombok.Data;

import java.io.Serializable;

//@Data
public class Spot implements Serializable {
    private static final long serialVersionUID = -5276940640259749850L;

    public int x;
    public int y;

    public Spot(int x, int y) {
        this.x = x;
        this.y = y;
    }
//    public Spot(){
//        this.x = 0;
//        this.y = 0;
//    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

//    public String toString() {
//        return "Spot[x=" + x + ",y=" + y + "]";
//    }

}
