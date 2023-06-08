package brewster.chess.model.response;

import brewster.chess.model.constant.SpecialMove;
import brewster.chess.model.constant.Type;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@ToString
public class PieceMoves {
    private final List<Integer> validMoves;
    private Map<Integer, SpecialMove> specialMoves;

//    private boolean isPromotion = false;
//    private List<Type> promotionOptions;
//
//    public PieceMoves(List<Integer> validMoves, Map<Integer, SpecialMove> specialMoves, List<Type> promotionOptions) {
//        this.validMoves = validMoves;
//        this.specialMoves = specialMoves;
//        this.promotionOptions = promotionOptions;
//        this.isPromotion = true;
//    }
    public PieceMoves(List<Integer> validMoves, Map<Integer, SpecialMove> specialMoves) {
        this.validMoves = validMoves;
        this.specialMoves = specialMoves;
    }
    public PieceMoves(List<Integer> validMoves) {
        this.validMoves = validMoves;
    }
}
