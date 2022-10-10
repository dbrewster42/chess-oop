package brewster.chess.model.response;

import brewster.chess.model.Game;
import brewster.chess.model.piece.Piece;
import lombok.Data;

import java.util.List;

@Data
public class GameResponse {
    private boolean active;
    private boolean check;
    private String message;
//    private String playersName;
    private boolean isWhite;
    private List<Piece> whitePlayers;
    private List<Piece> blackPlayers;

//    public GameResponse(String message, boolean isWhite, List<Piece> whitePlayers, List<Piece> blackPlayers) {
//        this.message = message;
//        this.isWhite = isWhite;
//        this.whitePlayers = whitePlayers;
//        this.blackPlayers = blackPlayers;
//        this.active = true;
//        this.check = false;
//    }
    public GameResponse(Game game) {
        this.message = game.getMoves();
        this.isWhite = game.isWhitesTurn();
        this.whitePlayers = game.getPlayer1().getPieces();
        this.blackPlayers = game.getPlayer2().getPieces();
        this.check = game.isCheck();
        this.active = true;
    }
    public GameResponse(String message, boolean isWhite, List<Piece> whitePlayers, List<Piece> blackPlayers, boolean isCheck) {
        this.message = message;
        this.isWhite = isWhite;
        this.whitePlayers = whitePlayers;
        this.blackPlayers = blackPlayers;
        this.check = isCheck;
        this.active = true;
    }

    public GameResponse(boolean active, String player1, String player2) {
        this.active = active;
        this.message = player1 + " has checkmated " + player2 + "! " + player1 + " wins!";
    }
}
