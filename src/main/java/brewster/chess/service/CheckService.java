package brewster.chess.service;

import brewster.chess.model.Game;
import brewster.chess.model.request.MoveRequest;
import brewster.chess.piece.Piece;

import java.awt.Point;
import java.util.List;

public class CheckService {
    public boolean didCheck(List<Piece> friends, List<Piece> foes, List<Point> spots) {
        Point kingsLocation = foes.get(0).getSpot();
        for (Piece friend : friends){
            if (friend.calculateLegalMoves(spots, foes).contains(kingsLocation)){
                return true;
            }
        }
        return false;
    }
    public boolean didCheckMate(Game game) {
        return false;
    }

    public boolean didDefeatCheck(Game game) {
        return false;
    }
    //can efficiently prevent moving into check by seeing if old position was on a line with the king
    public boolean movedIntoCheck(Game game, MoveRequest request) {
        return false;
    }
}