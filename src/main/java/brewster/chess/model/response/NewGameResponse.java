package brewster.chess.model.response;

import brewster.chess.model.Game;
import lombok.Data;

import java.util.Map;

import static brewster.chess.util.ImageMatch.convertPiecesToResponse;

@Data
public class NewGameResponse {
    private long id;
    private StatusResponse status;
    private String whitePlayer;
    private String blackPlayer;
    private Map<Integer, String> pieces;


    public NewGameResponse(Game game){
        this.id = game.getId();
        this.status = new StatusResponse(game);
        this.whitePlayer = game.getPlayer1().getName();
        this.blackPlayer = game.getPlayer2().getName();
        this.pieces = convertPiecesToResponse(game);
    }

}
