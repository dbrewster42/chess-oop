package brewster.chess.exception;

import lombok.Getter;

@Getter
public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(boolean isCheck) {
        super(isCheck ? "You must move out of check" : "You cannot move into check");
    }

}
