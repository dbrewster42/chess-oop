package brewster.chess.model;

import brewster.chess.piece.Piece;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

import static brewster.chess.util.TeamCreation.getNewTeam;

@Getter
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private final boolean isWhite;
    @OneToMany
    private final List<Piece> pieces;
    @ManyToOne
    private final User user;

    public Player(User user, boolean isWhite) {
        this.user = user;
        this.isWhite = isWhite;
        this.pieces = generatePieces();
    }

    public String getName(){
        return user.getName();
    }

    private List<Piece> generatePieces(){
        return getNewTeam(isWhite);
    }

}
