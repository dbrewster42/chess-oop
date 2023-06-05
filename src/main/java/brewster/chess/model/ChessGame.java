package brewster.chess.model;

import brewster.chess.exception.PieceNotFound;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.Square;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Entity
public class ChessGame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = {CascadeType.ALL})
    private final Player whitePlayer;
    @OneToOne(cascade = CascadeType.ALL)
    private final Player blackPlayer;
    @ElementCollection
//    private List<String> moves;
    private List<Move> moves;

    private boolean isWhitesTurn = true;
//    private boolean isActive = true;
    private boolean isCheck = false;

    public ChessGame(User user1, User user2) {
        this.whitePlayer = new WhitePlayer(user1, this);
        this.blackPlayer = new BlackPlayer(user2, this);
//        user1.addPlayer(whitePlayer);
//        user2.addPlayer(blackPlayer);
        moves = new ArrayList<>();
    }
    public ChessGame(){
        this.whitePlayer = null;
        this.blackPlayer = null;
    }

    public ChessGame changeTurn() {
        this.isWhitesTurn = !isWhitesTurn;
        return this;
    }

    public Piece getOwnPiece(int position) {
        return potentialPiece(getCurrentTeam(), position).orElseThrow(PieceNotFound::new);
    }
    public Optional<Piece> getPotentialFoe(int position) {
        return potentialPiece(getFoesPieces(), position);
    }

    public Optional<Piece> potentialPiece(List<Piece> pieces, int position) {
        return pieces.stream()
            .filter(piece -> piece.isAtPosition(position))
            .findAny();
    }

    public List<Piece> getAllPieces(){
        return Stream.concat(whitePlayer.getPieces().stream(), blackPlayer.getPieces().stream()).collect(Collectors.toList());
    }
    public List<Square> getAllOccupiedSquares(){
        return getAllPieces().stream()
            .map(Piece::getSquare)
            .collect(Collectors.toList());
    }

    public String getCurrentPlayerName(){
        return getCurrentPlayer().getName();
    }
    public List<Piece> getFoesPieces(){
        return getOpponent().getPieces();
    }
    public List<Piece> getCurrentTeam(){
        return getCurrentPlayer().getPieces();
    }
    public Player getCurrentPlayer(){
        return isWhitesTurn ? whitePlayer : blackPlayer;
    }
    public Player getOpponent(){
        return isWhitesTurn ? blackPlayer : whitePlayer;
    }

    public void restart(){
        isWhitesTurn = true;
        isCheck = false;
        moves = new ArrayList<>();
    }
}
