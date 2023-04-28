package brewster.chess.service.model;

import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.Square;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GamePiecesDto {
    private final List<Square> squares;
    private final List<Piece> friends;
    private final List<Piece> foes;
}
