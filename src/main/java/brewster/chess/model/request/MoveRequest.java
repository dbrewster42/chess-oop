package brewster.chess.model.request;

import lombok.Data;

@Data
public class MoveRequest {
    private int start;
    private int end;
}
