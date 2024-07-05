package brewster.chess.service;

import brewster.chess.model.piece.Piece;
import brewster.chess.model.request.MoveRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoveMessageService {
    private static final String COLUMNS = " ABCDEFGH";

    public String getMoveMessage(Piece piece, MoveRequest request, boolean isCheck, Optional<Piece> potentialFoe){
        StringBuilder message = standardMessage(piece, request);
        Optional.ofNullable(request.getSpecialMove()).ifPresent(s -> message.append(addSpecialMoveMessage(request)));
        potentialFoe.ifPresent(foe -> message.append(" and captures a ").append(foe.getType()));
        if (isCheck) { message.append(" - CHECK!"); }
        return message.toString();
//        game.getMoves().add(message.toString());
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

    private StringBuilder standardMessage(Piece piece, MoveRequest request) {
        return new StringBuilder(piece.getTeam() + " " + piece.getType() + pieceMovement(request));
    }

    private String pieceMovement(MoveRequest request) {
        return squareName(" ", request.getStart()) + squareName(" to ", request.getEnd());
    }
    private String squareName(String prefix, int location) {
        return prefix + COLUMNS.charAt(location / 10) + location % 10;
    }
}
