package brewster.chess.util;

import brewster.chess.model.Game;
import brewster.chess.model.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public class ImageMatch {

    public static Map<Integer, String> convertPiecesToResponse(Game game){
        Map<Integer, String> spotAndPicture = new HashMap<>();
        for (Piece piece : game.getPlayer1().getPieces()){
            spotAndPicture.put(piece.getLocation(), convertToImage(piece, 'w'));
        }
        for (Piece piece : game.getPlayer2().getPieces()){
            spotAndPicture.put(piece.getLocation(), convertToImage(piece, 'b'));
        }
        return spotAndPicture;
    }

    private static String convertToImage(Piece piece, char prefix){
        return prefix + piece.getType().name.toLowerCase() + ".svg.png";
    }
}