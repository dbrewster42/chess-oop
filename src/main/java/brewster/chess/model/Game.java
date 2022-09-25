package brewster.chess.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @OneToOne(cascade = {CascadeType.ALL})
    private final Player player1;
    @OneToOne(cascade = CascadeType.ALL)
    private final Player player2;
//    private Status status;
    private boolean isWhitesTurn = true;
    private boolean isActive = true;
    private boolean isCheck = false;

    public Game(User user1, User user2) {
        this.player1 = new Player(user1, true);
        user1.addPlayer(player1);
        this.player2 = new Player(user2, false);
        user2.addPlayer(player2);
    }
    public Game(){
        this.player1 = null;
        this.player2 = null;
    }
}
