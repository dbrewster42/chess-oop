package brewster.chess.model.response;

import brewster.chess.model.Game;
import lombok.Data;

import java.util.Map;

import static brewster.chess.util.ImageMatch.convertPiecesToResponse;

@Data
public class GameResponse {
    private boolean active;
    private boolean check;
    private String message;
    private boolean isWhite;
    private Map<Integer, String> pieces;
//    private List<Piece> whitePlayers;
//    private List<Piece> blackPlayers;


    public GameResponse(Game game) {
        this.message = game.getMoves();
        this.isWhite = game.isWhitesTurn();
        this.check = game.isCheck();
        this.active = true;
        this.pieces = convertPiecesToResponse(game);
    }

    public GameResponse(boolean active, String player1, String player2) {
        this.active = active;
        this.message = player1 + " has checkmated " + player2 + "! " + player1 + " wins!";
    }
}
