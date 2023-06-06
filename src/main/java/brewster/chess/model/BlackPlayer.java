package brewster.chess.model;

import brewster.chess.model.constant.Team;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import static brewster.chess.util.TeamCreation.generatePieces;

@Getter
@Entity
public class BlackPlayer extends Player {
    @OneToOne(mappedBy = "blackPlayer")
    private final ChessGame game;

    public BlackPlayer(User user, ChessGame game) {
        super(user, generatePieces(Team.BLACK));
        this.game = game;
    }
    public BlackPlayer(){ game = null; }

}