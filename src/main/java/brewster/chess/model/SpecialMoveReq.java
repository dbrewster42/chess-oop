package brewster.chess.model;

import brewster.chess.model.constant.SpecialMove;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class SpecialMoveReq {
    private SpecialMove specialMove;
    private List<Integer> moves;
}
