package brewster.chess.model.request;

import brewster.chess.model.constant.Type;
import lombok.Getter;

@Getter
public class PromotionRequest {
    private final Type type;
    private final int oldPosition;
    private final int newPosition;

    public PromotionRequest(String type, int oldPosition, int newPosition) {
        this.type = Type.valueOf(type);
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }
}
