package brewster.chess.mother;

import brewster.chess.piece.King;
import brewster.chess.piece.Pawn;
import brewster.chess.piece.Piece;
import brewster.chess.piece.Queen;

import java.util.ArrayList;
import java.util.List;

import static brewster.chess.model.constant.Team.BLACK;
import static brewster.chess.model.constant.Team.WHITE;

public class PieceMother {

    public static List<Piece> getAllPieces() {
        return List.of(getWhiteKing(), getBlackKing(), getWhitePawn(), getBlackPawn(), getWhiteQueen(), getBlackQueen());
    }

    public static Piece getWhiteKing(){
        return new King(WHITE, 5, 1);
    }

    public static Piece getBlackKing(){
        return new King(BLACK, 5, 8);
    }

    public static Piece getWhitePawn(){
        return new Pawn(WHITE, 8, 2);
    }

    public static Piece getBlackPawn(){
        return new Pawn(BLACK, 8, 7);
    }

    public static Piece getWhiteQueen(){
        return new Queen(WHITE, 4, 1);
    }

    public static Piece getBlackQueen(){
        return new Queen(BLACK, 4, 8);
    }
}
