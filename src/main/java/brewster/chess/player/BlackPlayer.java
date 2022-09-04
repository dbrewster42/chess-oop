package brewster.chess.player;

import brewster.chess.model.User;
import brewster.chess.piece.Piece;

import java.util.List;

public class BlackPlayer extends Player {

    public BlackPlayer(User user, boolean isWhite) {
        super(user, isWhite);
    }

    @Override
    List<Piece> generatePieces() {
        return null;
    }
}
