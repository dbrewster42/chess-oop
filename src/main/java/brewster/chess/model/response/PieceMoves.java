package brewster.chess.model.response;

import brewster.chess.model.constant.SpecialMove;
import brewster.chess.model.constant.Type;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class PieceMoves {
    private final List<Integer> validMoves;
    private Map<Integer, SpecialMove> specialMoves;
//    private List<Integer> specialMoves;

    private boolean isPromotion = false;
    private List<Type> promotionOptions;

    public PieceMoves(List<Integer> validMoves) {
        this.validMoves = validMoves;
    }

    public void addSpecialMoves(Map<Integer, SpecialMove> specialMoves) {
        this.specialMoves = specialMoves;
    }
    public void addPromotionOptions() {
        this.isPromotion = true;
        this.promotionOptions = Type.promotionChoices();
    }
}
