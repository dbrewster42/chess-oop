package brewster.chess.model.response;

import brewster.chess.piece.Promotion;
import lombok.Getter;

import java.awt.Point;

@Getter
public class PromotionResponse {
    private final Point spot;
    private final int x;
    private final int y;
    public PromotionResponse(Promotion promotion) {
        this.spot = promotion.getSpot();
        this.x = promotion.getX();
        this.y = promotion.getY();
    }

    @Override
    public String toString(){
        return String.format("Your pawn will be promoted after you moved from %s to %s%s", spot, x, y);
    }
}
