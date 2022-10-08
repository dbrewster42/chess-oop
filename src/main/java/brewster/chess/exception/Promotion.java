package brewster.chess.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import brewster.chess.piece.Spot;

@Getter
@AllArgsConstructor
public class Promotion extends RuntimeException {
    private Spot spot;
    private int x;
    private int y;
}
