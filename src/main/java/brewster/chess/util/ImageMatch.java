package brewster.chess.util;

import brewster.chess.model.ChessGame;
import brewster.chess.model.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public class ImageMatch {

    public static Map<Integer, String> getPiecesMap(ChessGame game){
        Map<Integer, String> pieces = new HashMap<>();
        for (Piece piece : game.getWhitePlayer().getPieces()){
            pieces.put(piece.location(), convertToImage('w', piece));
        }
        for (Piece piece : game.getBlackPlayer().getPieces()){
            pieces.put(piece.location(), convertToImage('b', piece));
        }
        return pieces;
    }

    private static String convertToImage(char prefix, Piece piece){
        return prefix + piece.getType().name.toLowerCase() + ".svg.png";
    }
}
