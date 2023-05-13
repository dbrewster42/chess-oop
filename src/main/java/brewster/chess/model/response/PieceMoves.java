package brewster.chess.model.response;

import brewster.chess.model.constant.SpecialMove;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PieceMovesResponse {
    private List<Integer> validMoves;
    private Map<Integer, SpecialMove> specialMoves;
//    private Map<SpecialMove, List<Integer>> specialMoves;
    public PieceMovesResponse(List<Integer> validMoves) {
        this.validMoves = validMoves;
    }
}
