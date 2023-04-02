package brewster.chess.model.response;

import brewster.chess.model.Game;
import lombok.Data;

import java.util.Map;

import static brewster.chess.util.ImageMatch.convertPiecesToResponse;

@Data
public class GameResponse {
//    private boolean active;
//    private boolean check;
//    private boolean isWhite;
    private StatusResponse status;
    private String moves;
    private Map<Integer, String> pieces;
//    private List<Piece> whitePlayers;
//    private List<Piece> blackPlayers;

    public GameResponse(Game game) {
        this.moves = game.getMoves();
        this.pieces = convertPiecesToResponse(game);
        this.status = new StatusResponse(game);
    }
//    public GameResponse(Game game) {
//        this.message = game.getMoves();
//        this.isWhite = game.isWhitesTurn();
//        this.check = game.isCheck();
//        this.active = true;
//        this.pieces = convertPiecesToResponse(game);
//    }

    public GameResponse(String player1, String player2) {
        this.status = new StatusResponse();
        this.moves = player1 + " has checkmated " + player2 + "! " + player1 + " wins!";
    }
    public GameResponse() {}
}
