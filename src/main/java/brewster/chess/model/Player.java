package brewster.chess.model;

import brewster.chess.model.piece.Piece;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Player {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private User user;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Piece> pieces;

    public Player(User user, List<Piece> pieces) {
        this.user = user;
        this.pieces = pieces;
    }
    public Player() { }

    public abstract ChessGame getGame();
    public String getName() {
        return user.getName();
    }

}
