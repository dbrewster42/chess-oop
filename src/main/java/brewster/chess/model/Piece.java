package brewster.chess.model;

import brewster.chess.model.constant.Team;
import brewster.chess.service.PieceService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.Point;
import java.util.List;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
//@Entity
public abstract class Piece {
    private Team team;
    private int x;
    private int y;

//    public abstract <T extends Piece> T returnType();
    public abstract List<Point> calculatePotentialMoves(Stream<Piece> allPieces, PieceService pieceService);
}
