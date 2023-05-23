package brewster.chess.service;

import brewster.chess.model.ChessGame;
import brewster.chess.model.constant.SpecialMove;
import brewster.chess.model.constant.Type;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.request.MoveRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoveMessageService { //todo make moveService with Move?
    private static final String COLUMNS = " ABCDEFGH";

    public void addMove(ChessGame game, Type type, MoveRequest request, Optional<Piece> potentialFoe){
        StringBuilder message = standardMessage(game, type, request);
        Optional.ofNullable(request.getSpecialMove()).ifPresent(s -> message.append(addSpecialMoveMessage(request)));
        potentialFoe.ifPresent(foe -> message.append(" and has captured a ").append(foe.getType()));
        if (game.isCheck()) { message.append(" - CHECK!"); }
        game.getMoves().add(message.toString());
    }

    private String addSpecialMoveMessage(MoveRequest request) {
        switch (request.getSpecialMove()) {
            case Castle:
                return " and performed a castle";
            case Passant:
                return " in an En Passant";
            case Promotion:
                return " and was promoted to a " + request.getPromotionType();
            default:
                throw new RuntimeException("oh no");
        }
    }

    private StringBuilder standardMessage(ChessGame game, Type type, MoveRequest request) {
        return new StringBuilder(game.getCurrentPlayerName() + " has moved his " + type + pieceMovement(request));
    }

    private String pieceMovement(MoveRequest request) {
        return squareName(" from ", request.getStart()) + squareName(" to ", request.getEnd());
    }
    private String squareName(String prefix, int location) {
        return prefix + COLUMNS.charAt(location / 10) + location % 10;
    }
}
