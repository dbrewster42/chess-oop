package brewster.chess.model;

import brewster.chess.model.piece.Piece;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private final List<Piece> pieces;
    @ManyToOne
    private final User user;

    public Player(User user, boolean isWhite) {
        this.user = user;
        this.isWhite = isWhite;
        this.pieces = generatePieces();
    }
    public Player(){
        this.user = null;
        this.isWhite = true;
        this.pieces = null;
    }

//    public void restart(){
//        pieces = generatePieces();
//    }

    public String getName(){
        return user.getName();
    }

    private List<Piece> generatePieces(){
        return getNewTeam(isWhite);
    }

}
