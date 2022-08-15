package brewster.chess.model;

import brewster.chess.piece.Pawn;
import brewster.chess.piece.Piece;
import lombok.Data;

import java.util.List;

@Data
public class Player {
    private User user;
    private List<Piece> pieces;
    private boolean isWhite;

    public Player(User user, boolean isWhite) {
        this.user = user;
        this.isWhite = isWhite;
        this.pieces = generatePieces();
    }

    private List<Piece> generatePieces() {
        return List.of(new Pawn());
    }
}
