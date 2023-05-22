package brewster.chess.api;

import brewster.chess.exception.GameNotFound;
import brewster.chess.exception.InvalidMoveException;
import brewster.chess.exception.PieceNotFound;
import brewster.chess.exception.UserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(value = InvalidMoveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error resolveInvalidMoveException(InvalidMoveException e){
        log.info("invalid move {}", e.getMessage());
        return buildError(e);
    }

    @ExceptionHandler(value = { GameNotFound.class, PieceNotFound.class, UserNotFound.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error resolveNotFound(Exception e){
        log.info("error finding {} - {}", e.getClass(), e.getMessage());
        return buildError(e);
    }

    private Error buildError(Exception e){
        return new Error(e.getMessage(), e);
    }
}
