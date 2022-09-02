package brewster.chess.model.response;

import brewster.chess.piece.Piece;
import lombok.Data;

import java.util.List;

@Data
public class GameResponse {
    private boolean active;
    private boolean check = false;
    private String message;
    private String playerName;
    private boolean isWhite;
    private List<Piece> whitePlayers;
    private List<Piece> blackPlayers;
}
