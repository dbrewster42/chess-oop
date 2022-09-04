package brewster.chess.player;

import brewster.chess.model.User;
import brewster.chess.piece.Piece;

import java.util.List;

public class WhitePlayer extends Player {

    public WhitePlayer(User user, boolean isWhite) {
        super(user, isWhite);
    }

    @Override
    List<Piece> generatePieces() {
        return null;
    }
}
