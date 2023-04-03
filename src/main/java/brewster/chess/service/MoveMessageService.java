package brewster.chess.service;

import brewster.chess.model.Game;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.request.MoveRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoveMessageService {
    private static final String COLUMNS = " ABCDEFGH";

    public void updateMoveMessage(Game game, String pieceName, MoveRequest request, Optional<Piece> potentialFoe){
        StringBuilder message = getStandardMessage(game, pieceName, request);
        potentialFoe.ifPresent(foe -> message.append(" and has captured a ").append(foe.getType()));
        if (game.isCheck()) { message.append(" - CHECK!"); }
        game.setMoves(message.append("\n").toString());
    }

    private StringBuilder getStandardMessage(Game game, String pieceName, MoveRequest request) {
        int turn = game.getMoves().split("\n").length;
        StringBuilder message = new StringBuilder(game.getMoves() + turn + ". " + game.getCurrentPlayerName());
        message.append(" has moved his ").append(pieceName).append(getPieceMovement(request));
        return message;

    }

    private String getPieceMovement(MoveRequest request) {
        return getSquareName(" from ", request.getStart()) + getSquareName(" to ", request.getEnd());
    }
    private String getSquareName(String prefix, int location) {
        return prefix + COLUMNS.charAt(location / 10) + location % 10;
    }
}
