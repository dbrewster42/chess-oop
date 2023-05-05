package brewster.chess.model.response;

import brewster.chess.model.ChessGame;
import lombok.Data;

@Data
public class StatusResponse {
    private boolean isActive;
    private boolean isCheck;
    private boolean isWhite;
    private String currentPlayer;

    public StatusResponse(ChessGame game) {
        this.isActive = true;
        this.isWhite = game.isWhitesTurn();
        this.isCheck = game.isCheck();
        this.currentPlayer = game.getCurrentPlayerName();
    }

    public StatusResponse() {
        this.isActive = false;
    }
}