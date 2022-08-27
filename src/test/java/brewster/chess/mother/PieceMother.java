package brewster.chess.mother;

import brewster.chess.piece.King;
import brewster.chess.piece.Pawn;
import brewster.chess.piece.Piece;

import static brewster.chess.model.constant.Team.BLACK;
import static brewster.chess.model.constant.Team.WHITE;

public class PieceMother {
    public static Piece getWhiteKing(){
        return new King(WHITE, 4, 1);
    }

    public static Piece getBlackKing(){
        return new King(BLACK, 4, 8);
    }

    public static Piece getWhitePawn(){
        return new Pawn(WHITE, 8, 2);
    }

    public static Piece getBlackPawn(){
        return new Pawn(BLACK, 8, 7);
    }
}
