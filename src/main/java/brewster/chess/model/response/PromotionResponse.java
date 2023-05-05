package brewster.chess.model.response;

import brewster.chess.model.ChessGame;
import brewster.chess.model.request.MoveRequest;
import lombok.Getter;

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

    public PromotionResponse(ChessGame game, MoveRequest request) {
        super(game);
        this.spot = request.getEnd();
    }

    @Override
    public String toString(){
        return String.format("Your pawn is being promoted after reaching %s", spot);
    }
}
