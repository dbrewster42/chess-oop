package brewster.chess.model;

import brewster.chess.model.constant.SpecialMove;
import brewster.chess.model.constant.Type;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.request.MoveRequest;
import lombok.Data;

import javax.persistence.Embeddable;
import java.util.Optional;

@Data
@Embeddable
public class Move {
    private Type pieceType;
    private int start;
    private int end;

    private SpecialMove specialMove;
    private Type potentialFoe;

    public Move(Type pieceType, MoveRequest request, Optional<Piece> potentialFoe) {
        this.pieceType = pieceType;
        this.start = request.getStart();
        this.end = request.getEnd();
        this.specialMove = request.getSpecialMove();
        potentialFoe.ifPresent(piece -> this.potentialFoe = piece.getType());
    }
    public Move() {}
}
