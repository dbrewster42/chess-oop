package brewster.chess.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

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
    public WhitePlayer(){ game = null; }



}