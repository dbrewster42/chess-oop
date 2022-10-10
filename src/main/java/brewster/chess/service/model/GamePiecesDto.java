package brewster.chess.service.model;

import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.Spot;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GamePiecesDto {
    private final List<Spot> spots;
    private final List<Piece> friends;
    private final List<Piece> foes;
}
