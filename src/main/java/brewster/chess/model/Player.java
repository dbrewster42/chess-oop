package brewster.chess.model;

import brewster.chess.piece.Pawn;
import brewster.chess.piece.Piece;
import lombok.Data;

import javax.persistence.Entity;
import java.util.List;

@Data
@Entity
public class Player {
    private final User user;
    private final List<Piece> pieces;
    private final boolean isWhite;

    public Player(User user, boolean isWhite) {
        this.user = user;
        this.isWhite = isWhite;
        this.pieces = generatePieces();
    }

    private List<Piece> generatePieces() {
        return List.of(new Pawn(2, 2));
    }
}
