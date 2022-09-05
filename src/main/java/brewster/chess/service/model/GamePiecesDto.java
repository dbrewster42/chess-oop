package brewster.chess.service.model;

import brewster.chess.piece.Piece;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.Point;
import java.util.List;

@Getter
@AllArgsConstructor
public class GamePiecesDto {
    private final List<Point> spots;
    private final List<Piece> friends;
    private final List<Piece> foes;
}
