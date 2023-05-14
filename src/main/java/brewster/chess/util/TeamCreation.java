package brewster.chess.util;

import brewster.chess.model.constant.Team;
import brewster.chess.model.piece.Bishop;
import brewster.chess.model.piece.King;
import brewster.chess.model.piece.Knight;
import brewster.chess.model.piece.Pawn;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.Queen;
import brewster.chess.model.piece.Rook;

import java.util.ArrayList;
import java.util.List;

import static brewster.chess.model.constant.Team.WHITE;

public class TeamCreation {
    public static List<Piece> generatePieces(Team team) {
        if (team == WHITE){
            return generatePieces(1, 2, team);
        }  else {
            return generatePieces(8, 7, team);
        }
    }
    private static List<Piece> generatePieces(int y, int pawnY, Team team) {
        List<Piece> allPieces = new ArrayList<>();

        allPieces.add(new King(team, 5, y));
        allPieces.add(new Queen(team, 4, y));

        allPieces.add(new Rook(team, 1, y));
        allPieces.add(new Knight(team,2, y));
        allPieces.add(new Bishop(team, 3, y));
        allPieces.add(new Bishop(team, 6, y));
        allPieces.add(new Knight(team, 7, y));
        allPieces.add(new Rook(team, 8, y));

        for (int x = 1; x < 9; x++){
            allPieces.add(new Pawn(team, x, pawnY));
        }

        return allPieces;
    }
}
