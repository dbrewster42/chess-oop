package brewster.chess.model.response;

import brewster.chess.piece.Piece;

import java.util.List;

public class PieceResponse {
    private List<Piece> thisTeam;
    private List<Piece> otherTeam;
    private int count;

    public PieceResponse(List<Piece> thisTeam, List<Piece> otherTeam){
        this.thisTeam = thisTeam;
        count = thisTeam.size();
        this.otherTeam = otherTeam;
    }
}
