package brewster.chess.model.piece;

import java.io.Serializable;
import java.util.Objects;

public class Square implements Serializable {
    private static final long serialVersionUID = -5276940640259749850L;

    public int x;
    public int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Square(int position) {
        this.x = position / 10;
        this.y = position % 10;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void move(int newPosition) {
        this.x = newPosition / 10;
        this.y = newPosition % 10;
    }

    public int intValue(){
        return x * 10 + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return x == square.x && y == square.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Square{" + "x=" + x + ", y=" + y + '}';
    }
}
