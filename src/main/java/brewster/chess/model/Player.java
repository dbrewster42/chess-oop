package brewster.chess.model;

import brewster.chess.model.piece.Piece;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Player {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    List<Piece> pieces;
    @ManyToOne
    User user;

    public Player(){
        this.user = null;
        this.pieces = null;
    }

    public abstract ChessGame getGame();
    public String getName() {
        return user.getName();
    }

}
