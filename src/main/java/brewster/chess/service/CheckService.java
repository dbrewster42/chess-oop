package brewster.chess.service;

import brewster.chess.model.Game;
import brewster.chess.model.request.MoveRequest;

public class CheckService {
    public boolean didCheck(Game game) {
        return false;
    }
    public boolean didCheckMate(Game game) {
        return false;
    }
    //can efficiently prevent moving into check by seeing if old position was on a line with the king
    public boolean movedIntoCheck(Game game, MoveRequest request) {
        return false;
    }
}