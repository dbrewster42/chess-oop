package brewster.chess.model;

import brewster.chess.model.Piece;
import brewster.chess.model.piece.Queen;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

import static brewster.chess.model.constant.Team.WHITE;

@Data
@Entity
public class Player {
    @Id
    private int id;
    private final User user;
    private final List<Piece> pieces;
    private final boolean isWhite;

    public Player(User user, boolean isWhite) {
        this.user = user;
        this.isWhite = isWhite;
        this.pieces = generatePieces();
    }

    private List<Piece> generatePieces() {
        return List.of(new Queen(WHITE, 2, 2));
    }
}
