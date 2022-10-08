package brewster.chess.model.response;

import brewster.chess.exception.Promotion;
import lombok.Getter;

import brewster.chess.piece.Spot;

@Getter
public class PromotionResponse {
    private final Spot spot;
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
