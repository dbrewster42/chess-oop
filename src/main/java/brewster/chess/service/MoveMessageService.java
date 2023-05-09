package brewster.chess.service;

import brewster.chess.model.ChessGame;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.request.MoveRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoveMessageService {
    private static final String COLUMNS = " ABCDEFGH";

    public void addMove(ChessGame game, String pieceName, MoveRequest request, Optional<Piece> potentialFoe){
        StringBuilder message = standardMessage(game, pieceName, request);
        potentialFoe.ifPresent(foe -> message.append(" and has captured a ").append(foe.getType()));
        if (game.isCheck()) { message.append(" - CHECK!"); }
        game.getMoves().add(message.toString());
//        game.setMoves(message.append("\n").toString());
    }

    private StringBuilder standardMessage(ChessGame game, String pieceName, MoveRequest request) {
        return new StringBuilder(game.getCurrentPlayerName() + " has moved his " + pieceName + pieceMovement(request));
//        return new StringBuilder(game.getCurrentPlayerName()).append(" has moved his ").append(pieceName).append(getPieceMovement(request));
    }

    private String pieceMovement(MoveRequest request) {
        return squareName(" from ", request.getStart()) + squareName(" to ", request.getEnd());
    }
    private String squareName(String prefix, int location) {
        return prefix + COLUMNS.charAt(location / 10) + location % 10;
    }
}
