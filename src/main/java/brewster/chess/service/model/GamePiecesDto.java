package brewster.chess.service.model;

import brewster.chess.piece.Piece;
import brewster.chess.piece.Spot;
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
