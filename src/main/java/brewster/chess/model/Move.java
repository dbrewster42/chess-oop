package brewster.chess.model;

import brewster.chess.model.constant.SpecialMove;
import brewster.chess.model.constant.Type;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.request.MoveRequest;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Optional;

//@Entity
@Data
@Embeddable
public class Move {
//    @Id
////    @GeneratedValue(strategy = GenerationType.AUTO)
//    private String id;
//    private ChessGame game;

//    private String pieceName;
    private Type pieceType;
    private int start;
    private int end;

    private SpecialMove specialMove;
    private Type potentialFoe;

    public Move(Type pieceType, MoveRequest request, Optional<Piece> potentialFoe) {
//        this.id = game.getId() + "-" + (game.getMoves().size() + 1);
        this.pieceType = pieceType;
        this.start = request.getStart();
        this.end = request.getEnd();
        this.specialMove = request.getSpecialMove();
        if (potentialFoe.isPresent()) {
            this.potentialFoe = potentialFoe.get().getType();
        }
    }
    public Move() {}
}
