package brewster.chess.model;

import brewster.chess.model.User;
import brewster.chess.piece.Piece;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

import static brewster.chess.util.TeamCreation.getNewTeam;

@Data
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private final String name;
    private final boolean isWhite;
    private final List<Piece> pieces;

//    @ManyToOne
//    private final User user;

    public Player(User user, boolean isWhite) {
//        this.user = user;
        this.name = user.getName();
        this.isWhite = isWhite;
        this.pieces = generatePieces();
    }

    private List<Piece> generatePieces(){
        return getNewTeam(isWhite);
    }

}
