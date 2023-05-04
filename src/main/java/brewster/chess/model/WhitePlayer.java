package brewster.chess.model;

import brewster.chess.model.piece.Piece;
import brewster.chess.util.TeamCreation;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

import static brewster.chess.util.TeamCreation.generatePieces;

@Getter
@Entity
public class WhitePlayer extends Player {

    @OneToOne(mappedBy = "whitePlayer")
    private final Game game;

    public WhitePlayer(User user, Game game) {
        this.user = user;
        this.game = game;
        this.pieces = generatePieces(true);
    }


}