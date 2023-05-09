package brewster.chess.model.request;

import brewster.chess.model.constant.SpecialMove;
import brewster.chess.model.constant.Type;
import lombok.Data;

@Data
public class MoveRequest {
    private int start;
    private int end;

    private SpecialMove specialMove;
    private Type promotionType;
}
