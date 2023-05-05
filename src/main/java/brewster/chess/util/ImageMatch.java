package brewster.chess.util;

import brewster.chess.model.ChessGame;
import brewster.chess.model.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public class ImageMatch {

    public static Map<Integer, String> convertPiecesToResponse(ChessGame game){
        Map<Integer, String> spotAndPicture = new HashMap<>();
        for (Piece piece : game.getWhitePlayer().getPieces()){
            spotAndPicture.put(piece.getLocation(), convertToImage(piece, 'w'));
        }
        for (Piece piece : game.getBlackPlayer().getPieces()){
            spotAndPicture.put(piece.getLocation(), convertToImage(piece, 'b'));
        }
        return spotAndPicture;
    }

    private static String convertToImage(Piece piece, char prefix){
        return prefix + piece.getType().name.toLowerCase() + ".svg.png";
    }
}
