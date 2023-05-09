package brewster.chess.model.response;

import brewster.chess.model.constant.SpecialMove;

import java.util.List;
import java.util.Map;

public class PieceMovesResponse {
    private List<Integer> validMoves;
    private Map<Integer, SpecialMove> specialMoves;
//    private Map<SpecialMove, List<Integer>> specialMoves;
}
