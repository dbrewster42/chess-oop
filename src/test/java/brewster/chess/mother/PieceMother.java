package brewster.chess.mother;

import brewster.chess.piece.King;
import brewster.chess.piece.Knight;
import brewster.chess.piece.Pawn;
import brewster.chess.piece.Piece;
import brewster.chess.piece.Queen;

import java.awt.Point;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static brewster.chess.model.constant.Team.BLACK;
import static brewster.chess.model.constant.Team.WHITE;

public class PieceMother {

    public static List<Piece> getAllPieces() {
        return Stream.of(getWhiteKing(), getBlackKing(), getWhitePawn(), getBlackPawn(), getWhiteQueen(), getBlackQueen()).collect(Collectors.toList());
    }

    public static List<Piece> getFoes(){
        return Stream.of(getBlackKing(), getBlackPawn(), getBlackQueen()).collect(Collectors.toList());
    }

    public static List<Point> getSpotsForKing(){
        return Stream.of(new Point(5, 8), new Point(8, 2), new Point(8, 7)).collect(Collectors.toList());
    }

    public static List<Point> getSpots2(){
        return Stream.of(new Point(4, 1), new Point(5, 1), new Point(5, 8), new Point(8, 2), new Point(8, 7), new Point(4, 8)).collect(Collectors.toList());
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

    public static Piece getEnterprisingBlackPawn(){
        return new Pawn(BLACK, 4, 2);
    }

    public static Piece getWhiteQueen(){
        return new Queen(WHITE, 4, 1);
    }

    public static Piece getBlackQueen(){
        return new Queen(BLACK, 4, 8);
    }

    public static Piece getWhiteKnight() { return new Knight(WHITE, 2, 1);
    }
}
