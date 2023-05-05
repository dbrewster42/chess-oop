package brewster.chess.service;

import brewster.chess.model.ChessGame;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.request.MoveRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoveMessageService {
    private static final String COLUMNS = " ABCDEFGH";

    public void updateMoveMessage(ChessGame game, String pieceName, MoveRequest request, Optional<Piece> potentialFoe){
        StringBuilder message = getStandardMessage(game, pieceName, request);
        potentialFoe.ifPresent(foe -> message.append(" and has captured a ").append(foe.getType()));
        if (game.isCheck()) { message.append(" - CHECK!"); }
        game.getMoves().add(message.toString());
//        game.setMoves(message.append("\n").toString());
    }

    private StringBuilder getStandardMessage(ChessGame game, String pieceName, MoveRequest request) {
////        int turn = game.getMoves().split("\n").length;
//        StringBuilder message = new StringBuilder(game.getCurrentPlayerName());
//        message.append(" has moved his ").append(pieceName).append(getPieceMovement(request));
        return new StringBuilder(game.getCurrentPlayerName() + " has moved his " + pieceName + getPieceMovement(request));
//        return new StringBuilder(game.getCurrentPlayerName()).append(" has moved his ").append(pieceName).append(getPieceMovement(request));
    }

    private String getPieceMovement(MoveRequest request) {
        return getSquareName(" from ", request.getStart()) + getSquareName(" to ", request.getEnd());
    }
    private String getSquareName(String prefix, int location) {
        return prefix + COLUMNS.charAt(location / 10) + location % 10;
    }
}
