package brewster.chess.model.response;

import brewster.chess.model.Game;
import brewster.chess.model.piece.Piece;
import lombok.Data;

import java.util.List;

@Data
public class NewGameResponse {
    private long id;
    private StatusResponse status;
    private String whitePlayerName;
    private String blackPlayerName;
    private List<Piece> whitePlayers;
    private List<Piece> blackPlayers;

    public NewGameResponse(Game game){
        this.id = game.getId();
        this.status = new StatusResponse(game);
        this.whitePlayerName = game.getPlayer1().getName();
        this.blackPlayerName = game.getPlayer2().getName();
        this.whitePlayers = game.getPlayer1().getPieces();
        this.blackPlayers = game.getPlayer2().getPieces();
    }

}
