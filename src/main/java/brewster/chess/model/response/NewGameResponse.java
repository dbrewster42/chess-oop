package brewster.chess.model.response;

import brewster.chess.model.ChessGame;
import lombok.Data;

import java.util.List;
import java.util.Map;

import static brewster.chess.util.ImageMatch.getPiecesMap;

@Data
public class NewGameResponse {
    private long id;
    private StatusResponse status;
    private String whitePlayer;
    private String blackPlayer;
    private List<String> promotionOptions;

    private Map<Integer, PieceMoves> allMoves;
    private Map<Integer, String> pieces;


    public NewGameResponse(ChessGame game){
        this.id = game.getId();
        this.status = new StatusResponse(game);
        this.whitePlayer = game.getWhitePlayer().getName();
        this.blackPlayer = game.getBlackPlayer().getName();
        this.promotionOptions = List.of("QUEEN", "ROOK", "BISHOP", "KNIGHT");
        this.pieces = getPiecesMap(game);
    }

}
