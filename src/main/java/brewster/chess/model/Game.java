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
    private boolean isWhitesTurn = true;
    private boolean isActive = true;
//    private boolean isCheck;

    public Game(int id, User user1, User user2) {
        this.id = id;
        this.player1 = new Player(user1, true);
        this.player2 = new Player(user2, false);
    }
}
