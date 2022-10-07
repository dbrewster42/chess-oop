package brewster.chess.model;

import brewster.chess.model.request.MoveRequest;
import brewster.chess.piece.Piece;
import lombok.Getter;

import javax.persistence.ManyToOne;
import java.util.Optional;

@Getter
public class Move {
    private String message;
    @ManyToOne
    private Piece piece;
    private int start;
    private int end;
    private boolean capture;
    private boolean check;

    private boolean passant;
    private boolean castle;

//    public Move(String playerName, Piece piece, int start) {
//        message = generateMessage(playerName);
////        message = playerName + " has moved his " + piece.getType() + " from " + start + " to " + piece.getSpot();
//    }
    public Move(String playerName, String pieceName, MoveRequest moveRequest, Optional<Piece> capturedPiece, boolean isCheck) {
//        message = generateMessage(playerName);
        message = playerName + " has moved his " + pieceName + " from " + moveRequest.getStart() + " to " + moveRequest.getEnd();
        capturedPiece.ifPresent(value -> message += " and has captured a " + value.getType().name);
        if (isCheck) { message += "and put his opponent in check!"; }
    }

//    public String generateMessage(String playerName) {
//        return playerName + " has moved his " + piece.getType() + " from " + start + " to " + piece.getSpot();
//    }
}