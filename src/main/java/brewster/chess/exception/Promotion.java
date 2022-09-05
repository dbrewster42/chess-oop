package brewster.chess.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.Point;

@Getter
@AllArgsConstructor
public class Promotion extends RuntimeException {
    private Point spot;
    private int x;
    private int y;
}
