package brewster.chess.model.response;

import brewster.chess.model.ChessGame;
import brewster.chess.model.constant.Type;
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
    private List<Type> promotionOptions;
//    private Map<Integer, String> pieces;

    private Map<Integer, PieceMoves> allMoves;
    private Map<Integer, String> pieces;

    public NewGameResponse(ChessGame game, List<Type> promotionOptions){
        this.id = game.getId();
        this.status = new StatusResponse(game);
        this.whitePlayer = game.getWhitePlayer().getName();
        this.blackPlayer = game.getBlackPlayer().getName();
        this.promotionOptions = promotionOptions;
        this.pieces = getPiecesMap(game);
    }
//    public NewGameResponse(ChessGame game, List<Type> promotionOptions, Map<Integer, String> pieces){
//        this(game, promotionOptions);
//        this.pieces = getPiecesMap(game);
//    }
//    public NewGameResponse(ChessGame game, Map<Integer, PieceMoves> allMoves){
//        this.id = game.getId();
////        this.status = new StatusResponse(game);
//        this.whitePlayer = game.getWhitePlayer().getName();
//        this.blackPlayer = game.getBlackPlayer().getName();
//        this.allMoves = allMoves;
//    }

}
