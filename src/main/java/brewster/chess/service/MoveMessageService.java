package brewster.chess.service;

import brewster.chess.model.Game;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.request.MoveRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoveMessageService {
    private final String columnName = " ABCDEFGH";

    public void updateMoveMessage(Game game, String pieceName, MoveRequest request, Optional<Piece> potentialFoe){
        int turn = game.getMoves().split("\\.").length;
        StringBuilder message = new StringBuilder(game.getMoves() + turn + ". " + getPlayerName(game));
        message.append(" has moved his ").append(pieceName).append(" from ").append(createSquareDisplayName(request));
//                .append(request.getStart()).append(" to ").append(request.getEnd());
        potentialFoe.ifPresent(foe -> message.append(" and has captured a ").append(foe.getType()));
        if (game.isCheck()) { message.append(" - CHECK!"); }
        game.setMoves(message.append("\n").toString());
    }

    private String createSquareDisplayName(MoveRequest request){
        char startX = columnName.charAt(request.getStart() / 10);
        char endX = columnName.charAt(request.getStart() / 10);

        return startX + request.getStart() % 10 + " to " + endX + request.getEnd() % 10;
    }
    private char getColumn(int y){
        String column =  " ABCDEFGH";
        return column.charAt(y);
    }

    public String getPlayerName(Game game){
        return game.isWhitesTurn() ? game.getPlayer1().getName() : game.getPlayer2().getName();
    }

    public String getPlayerNames(Game game){
        String player1 = game.getPlayer1().getName();
        String player2 = game.getPlayer2().getName();

        return game.isWhitesTurn() ? game.getPlayer1().getName() : game.getPlayer2().getName();
    }
    private String generateMessage(String playerName, Piece piece){
        return "";
    }
}
