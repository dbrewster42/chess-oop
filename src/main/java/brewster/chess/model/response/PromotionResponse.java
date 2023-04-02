package brewster.chess.model.response;

import brewster.chess.exception.Promotion;
import brewster.chess.model.request.MoveRequest;
import lombok.Getter;

import brewster.chess.model.piece.Spot;

@Getter
public class PromotionResponse extends GameResponse {
    private int spot;
//    private final Spot spot;
//    private final int x;
//    private final int y;
//    public PromotionResponse(Promotion promotion) {
//        this.spot = promotion.getSpot();
//        this.x = promotion.getX();
//        this.y = promotion.getY();
//    }

    public PromotionResponse(MoveRequest request) {
        super();
        this.spot = request.getEnd();
    }

    @Override
    public String toString(){
        return String.format("Your pawn is being promoted after reaching %s", spot);
    }
}
