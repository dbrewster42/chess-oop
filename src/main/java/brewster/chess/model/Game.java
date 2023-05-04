package brewster.chess.model;

import brewster.chess.model.piece.Piece;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = {CascadeType.ALL})
    private final Player whitePlayer;
    @OneToOne(cascade = CascadeType.ALL)
    private final Player blackPlayer;
    @ElementCollection
    private List<String> moves;

    private boolean isWhitesTurn = true;
    private boolean isActive = true;
    private boolean isCheck = false;

    public Game(User user1, User user2) {
        this.whitePlayer = new WhitePlayer(user1, this);
//        user1.addPlayer(whitePlayer);
        this.blackPlayer = new BlackPlayer(user2, this);
//        user2.addPlayer(blackPlayer);
        moves = new ArrayList<>();
    }
    public Game(){
        this.whitePlayer = null;
        this.blackPlayer = null;
    }

    public List<Piece> getAllPieces(){
        return Stream.concat(whitePlayer.getPieces().stream(), blackPlayer.getPieces().stream()).collect(Collectors.toList());
    }

    public String getCurrentPlayerName(){
        return isWhitesTurn ? whitePlayer.getName() : blackPlayer.getName();
    }

    public void restart(){
        isWhitesTurn = true;
        isCheck = false;
        moves = new ArrayList<>();
        isActive = true;
    }
}
