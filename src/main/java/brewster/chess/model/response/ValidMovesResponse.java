package brewster.chess.model.response;

import brewster.chess.model.constant.SpecialMove;
import brewster.chess.model.constant.Type;

import java.util.List;
import java.util.Map;

public class ValidMovesResponse {
    private  Map<Integer, PieceMovesResponse> allValidMoves;
    private List<Type> promotionOptions;
//    private List<Integer> validMoves;
//    private Map<SpecialMove, List<Integer>> specialMoves;
////    private boolean specialMove;
////    private List<Integer> specialMoves;
////    private boolean specialMove;
////    private List<Type> promotionOptions;
}
