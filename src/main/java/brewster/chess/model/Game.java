package brewster.chess.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Game {
    private final int id;
    private Player player1;
    private Player player2;
//    private Status status;
    private boolean isWhitesTurn;
    private boolean isActive = true;
//    private boolean isCheck;

}
