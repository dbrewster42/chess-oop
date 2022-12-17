package brewster.chess.model.response;

import brewster.chess.model.Game;
import brewster.chess.model.piece.Piece;
import lombok.Data;

import java.util.List;

import static brewster.chess.util.ImageMatch.convertPiecesToResponse;

@Data
public class NewGameResponse {
    private long id;
    private StatusResponse status;
    private String whitePlayerName;
    private String blackPlayerName;
    private List<Piece> pieces;


    public NewGameResponse(Game game){
        this.id = game.getId();
        this.status = new StatusResponse(game);
        this.whitePlayerName = game.getPlayer1().getName();
        this.blackPlayerName = game.getPlayer2().getName();
//        this.pieces = convertPiecesToResponse(game);
        this.pieces = game.getAllPieces();
    }

}
