package brewster.chess.exception;

public class GameNotFound extends RuntimeException {
    public GameNotFound() {
    }

    public GameNotFound(String message) {
        super(message);
    }
}
