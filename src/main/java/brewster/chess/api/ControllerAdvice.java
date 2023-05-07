package brewster.chess.api;

import brewster.chess.exception.InvalidMoveException;
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

    private Error buildError(Exception e){
        return new Error(e.getMessage(), e);
    }
}
