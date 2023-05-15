package brewster.chess.api;

import brewster.chess.exception.GameNotFound;
import brewster.chess.exception.InvalidMoveException;
import brewster.chess.exception.PieceNotFound;
import brewster.chess.exception.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = InvalidMoveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error resolveInvalidMoveException(InvalidMoveException e){
        return buildError(e);
    }

    @ExceptionHandler(value = { GameNotFound.class, PieceNotFound.class, UserNotFound.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error resolveNotFound(InvalidMoveException e){
        return buildError(e);
    }

    private Error buildError(Exception e){
        return new Error(e.getMessage(), e);
    }
}
