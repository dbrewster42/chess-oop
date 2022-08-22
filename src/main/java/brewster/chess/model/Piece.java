package brewster.chess.model;

import brewster.chess.model.constant.Team;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@Entity
public abstract class Piece {
    private Team team;
    private int x;
    private int y;
}
