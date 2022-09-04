package brewster.chess.player;

import brewster.chess.model.User;
import brewster.chess.piece.Piece;
import brewster.chess.piece.Queen;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

import static brewster.chess.model.constant.Team.WHITE;

@Data
@Entity
public abstract class Player {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private final String name;

//    @ManyToOne
//    private final User user;
    private final List<Piece> pieces;
    private final boolean isWhite;

    public Player(User user, boolean isWhite) {
//        this.user = user;
        this.name = user.getName();
        this.isWhite = isWhite;
        this.pieces = generatePieces();
    }

    abstract List<Piece> generatePieces();

}