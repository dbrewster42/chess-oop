package brewster.chess.model;

import brewster.chess.model.constant.Team;
import lombok.AllArgsConstructor;
import lombok.Data;


public class King extends Piece{
    public King(Team team, int x, int y) {
        super(team, x, y);
    }
}
