package brewster.chess.service;

import brewster.chess.model.Game;
import brewster.chess.model.Player;
import brewster.chess.piece.Piece;

public class GameService {

    public boolean isOccupied(Game game, int location){
        return game.getAllPieces().anyMatch(piece -> piece.isAtPosition(location));
    }
    public int[] calculatePossibleMoves(Game game, int position) {
        return getCurrentPlayer(game).getPieces().stream()
                .filter(piece -> piece.getLocation() == position)
                .findAny()
                .map(Piece::calculatePotentialMoves)
                .orElseThrow();
    }

    public Player getCurrentPlayer(Game game){
        return game.isWhitesTurn() ? game.getPlayer1() : game.getPlayer2();
    }
}
