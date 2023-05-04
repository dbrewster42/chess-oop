package brewster.chess.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import static brewster.chess.util.TeamCreation.generatePieces;

@Getter
@Entity
public class BlackPlayer extends Player {
    @OneToOne(mappedBy = "blackPlayer")
    private final Game game;

    public BlackPlayer(User user, Game game) {
        this.user = user;
        this.game = game;
        this.pieces = generatePieces(false);
    }
    public BlackPlayer(){ game = null; }

}