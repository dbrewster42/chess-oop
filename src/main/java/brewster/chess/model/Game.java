package brewster.chess.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
//    @OneToMany
//    private List<Move> moves;
    private String moves;
//    @ElementCollection(fetch = FetchType.EAGER)
//    private Set<String> moves;
    private boolean isWhitesTurn = true;
    private boolean isActive = true;
    private boolean isCheck = false;

    public Game(User user1, User user2) {
        this.player1 = new Player(user1, true);
        user1.addPlayer(player1);
        this.player2 = new Player(user2, false);
        user2.addPlayer(player2);
        moves = "";
//        moves = new HashSet<>();
    }
    public Game(){
        this.player1 = null;
        this.player2 = null;
    }
}
