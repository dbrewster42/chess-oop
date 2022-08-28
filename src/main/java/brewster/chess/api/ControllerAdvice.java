package brewster.chess.api;

import brewster.chess.model.response.PromotionResponse;
import brewster.chess.piece.Promotion;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = Promotion.class)
    public PromotionResponse resolvePromotion(Promotion promotion){
        return new PromotionResponse(promotion);
    }
}
