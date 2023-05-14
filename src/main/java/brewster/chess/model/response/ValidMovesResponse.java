package brewster.chess.model.response;

import brewster.chess.model.constant.Type;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ValidMovesResponse {
    private Map<Integer, PieceMoves> allValidMoves;
    private List<Type> promotionOptions;

//    private List<Integer> validMoves;
//    private Map<SpecialMove, List<Integer>> specialMoves;
////    private boolean specialMove;
////    private List<Integer> specialMoves;
////    private boolean specialMove;
////    private List<Type> promotionOptions;
}
