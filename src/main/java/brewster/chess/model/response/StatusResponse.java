package brewster.chess.model.response;

import brewster.chess.model.Game;
import lombok.Data;

@Data
public class StatusResponse {
    private boolean isActive;
    private boolean isCheck;
    private boolean isWhite;
    private String currentPlayer;

    public StatusResponse(Game game) {
        this.isActive = true;
        this.isWhite = game.isWhitesTurn();
        this.isCheck = game.isCheck();
        this.currentPlayer = game.getCurrentPlayerName();
    }

    public StatusResponse() {
        this.isActive = false;
    }
}