package brewster.chess.model.response;

import brewster.chess.model.ChessGame;
import lombok.Data;

import java.util.List;
import java.util.Map;

import static brewster.chess.util.ImageMatch.getPiecesMap;

@Data
public class
GameResponse {
    private StatusResponse status;
//    private List<String> moves;
    private String move;

    private Map<Integer, String> pieces;
    private Map<Integer, PieceMoves> allMoves;


    public GameResponse(ChessGame game, Map<Integer, PieceMoves> allMoves, String move) {
        this.pieces = getPiecesMap(game);
        this.status = new StatusResponse(game);
        this.allMoves = allMoves;
        this.move = move;
    }

    public GameResponse(String player1, String player2) {
        this.status = new StatusResponse();
        this.move = player1 + " has checkmated " + player2 + "! " + player1 + " wins!";
    }
    public GameResponse() {
        this.status = new StatusResponse();
        this.move ="The game has ended in a draw";
    }
}
