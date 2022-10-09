package brewster.chess.piece;

import java.io.Serializable;
import java.util.Objects;

public class Spot implements Serializable {
    private static final long serialVersionUID = -5276940640259749850L;

    public int x;
    public int y;

    public Spot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

//    public String toString() {
//        return "Spot[x=" + x + ",y=" + y + "]";
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spot spot = (Spot) o;
        return x == spot.x && y == spot.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
