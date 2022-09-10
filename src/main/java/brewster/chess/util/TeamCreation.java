package brewster.chess.util;

import brewster.chess.model.constant.Team;
import brewster.chess.piece.Bishop;
import brewster.chess.piece.King;
import brewster.chess.piece.Knight;
import brewster.chess.piece.Pawn;
import brewster.chess.piece.Piece;
import brewster.chess.piece.Queen;
import brewster.chess.piece.Rook;

import java.util.ArrayList;
import java.util.List;

import static brewster.chess.model.constant.Team.BLACK;
import static brewster.chess.model.constant.Team.WHITE;

public class TeamCreation {
    public static List<Piece> getNewTeam(boolean isWhite) {
        int y = 8;
        int pawnY = 7;
        Team team = BLACK;
        if (isWhite){
            y = 1;
            pawnY = 2;
            team = WHITE;
        }
        return getNewTeam(y, pawnY, team);
    }
    public static List<Piece> getNewTeam(int y, int pawnY, Team team) {
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
