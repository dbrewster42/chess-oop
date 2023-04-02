package brewster.chess.model;

import brewster.chess.model.piece.Piece;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Entity
public class Game {
    @Id
    @OneToOne(cascade = {CascadeType.ALL})
    private final Player player1;
    @OneToOne(cascade = CascadeType.ALL)
    private final Player player2;
//    @OneToMany
//    private List<Move> moves;
    private String moves;
//    @ElementCollection(fetch = FetchType.EAGER)
//    private Set<String> moves;
//    private Status status;
    private boolean isWhitesTurn = true;
    private boolean isActive = true;
    private boolean isCheck = false;

    public Game(User user1, User user2) {
        this.player1 = new Player(user1, true);
        user1.addPlayer(player1);
        this.player2 = new Player(user2, false);
        user2.addPlayer(player2);
        moves = "";
    }
    public Game(){
        this.player1 = null;
        this.player2 = null;
    }

    public List<Piece> getAllPieces(){
        return Stream.concat(player1.getPieces().stream(), player2.getPieces().stream()).collect(Collectors.toList());
    }

    public String getCurrentPlayerName(){
        return isWhitesTurn ? player1.getName() : player2.getName();
    }

    public void restart(){
        isWhitesTurn = true;
        isCheck = false;
        moves = "";
        isActive = true;
    }
}
