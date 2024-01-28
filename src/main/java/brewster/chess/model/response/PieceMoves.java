package brewster.chess.model.response;

import brewster.chess.model.constant.SpecialMove;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@ToString
public class PieceMoves {
    private final List<Integer> validMoves;
    private Map<Integer, SpecialMove> specialMoves;

    public PieceMoves(List<Integer> validMoves, Map<Integer, SpecialMove> specialMoves) {
        this.validMoves = validMoves;
        this.specialMoves = specialMoves;
    }
    public PieceMoves(List<Integer> validMoves) {
        this.validMoves = validMoves;
    }
}
