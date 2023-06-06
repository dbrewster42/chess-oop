package brewster.chess.model;

import brewster.chess.model.constant.Team;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import static brewster.chess.util.TeamCreation.generatePieces;

@Getter
@Entity
public class WhitePlayer extends Player {
    @OneToOne(mappedBy = "whitePlayer")
    private final ChessGame game;

    public WhitePlayer(User user, ChessGame game) {
        super(user, generatePieces(Team.WHITE));
        this.game = game;
    }
    public WhitePlayer(){ game = null; }

}