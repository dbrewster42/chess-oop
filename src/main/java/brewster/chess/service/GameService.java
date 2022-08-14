package brewster.chess.service;

import brewster.chess.model.Game;
import brewster.chess.model.Player;
import brewster.chess.piece.Piece;

public class GameService {
    public int[] calculatePossibleMoves(Game game, int position) {
//        Piece piece = getCurrentPlayer(game).getPieces().stream()
//                .filter(v -> v.getLocation() == position)
//                .findAny().orElseThrow();
//
//        return piece.calculatePotentialMoves();
        return getCurrentPlayer(game).getPieces().stream()
                .filter(v -> v.getLocation() == position)
                .findAny()
                .map(Piece::calculatePotentialMoves)
                .orElseThrow();
    }

    public Player getCurrentPlayer(Game game){
        return game.isWhitesTurn() ? game.getPlayer1() : game.getPlayer2();
    }
}
