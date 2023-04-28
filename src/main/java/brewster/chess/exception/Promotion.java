package brewster.chess.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import brewster.chess.model.piece.Square;

@Getter
@AllArgsConstructor
public class Promotion extends RuntimeException {
    private Square square;
    private int x;
    private int y;
}
