package brewster.chess.model;

import brewster.chess.piece.Piece;
import lombok.Data;

import java.util.List;

@Data
public class Player {
    private String name;
    private List<Piece> pieces;
    private boolean isWhite;
}
