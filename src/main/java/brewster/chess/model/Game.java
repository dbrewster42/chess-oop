package brewster.chess.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Game {
    @Id
    private final int id;
    private final Player player1;
    private final Player player2;
//    private Status status;
    private boolean isWhitesTurn = true;
    private boolean isActive = true;
//    private boolean isCheck = false;

    public Game(int id, User user1, User user2) {
        this.id = id;
        this.player1 = new Player(user1, true);
        this.player2 = new Player(user2, false);
    }

}
