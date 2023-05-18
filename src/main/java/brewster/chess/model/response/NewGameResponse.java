package brewster.chess.model.response;

import brewster.chess.model.ChessGame;
import lombok.Data;

import java.util.Map;

@Data
public class NewGameResponse {
    private long id;
//    private StatusResponse status;
    private String whitePlayer;
    private String blackPlayer;
//    private Map<Integer, String> pieces;
    private Map<Integer, PieceMoves> allMoves;


    public NewGameResponse(ChessGame game, Map<Integer, PieceMoves> allMoves){
        this.id = game.getId();
//        this.status = new StatusResponse(game);
        this.whitePlayer = game.getWhitePlayer().getName();
        this.blackPlayer = game.getBlackPlayer().getName();
        this.allMoves = allMoves;
    }

}
