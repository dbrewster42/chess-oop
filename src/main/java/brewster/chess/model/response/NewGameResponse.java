package brewster.chess.model.response;

import brewster.chess.model.Game;
import brewster.chess.player.Player;
import brewster.chess.piece.Piece;
import lombok.Data;

import java.util.List;

@Data
public class NewGameResponse {
    private long id;
    private String whitePlayerName;
    private String blackPlayerName;
    private List<Piece> whitePlayers;
    private List<Piece> blackPlayers;

    public NewGameResponse(long id, Player whitePlayer, Player blackPlayer){
        this.id = id;
        this.whitePlayerName = whitePlayer.getName();
        this.blackPlayerName = blackPlayer.getName();
        this.whitePlayers = whitePlayer.getPieces();
        this.blackPlayers = blackPlayer.getPieces();
    }

    public NewGameResponse(Game game, String whitePlayerName, String blackPlayerName){
        this.id = game.getId();
//        this.whitePlayerName = game.getPlayer1().getUser().getName();
        this.whitePlayerName = whitePlayerName;
        this.blackPlayerName = blackPlayerName;
        this.whitePlayers = game.getPlayer1().getPieces();
        this.blackPlayers = game.getPlayer2().getPieces();
    }
}
