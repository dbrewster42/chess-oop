package brewster.chess.model.piece;

import brewster.chess.Factory;
import brewster.chess.exception.PieceNotFound;
import brewster.chess.model.constant.Team;
import brewster.chess.model.constant.Type;

public class PieceFactory implements Factory<Piece> {

    private final Team team;
    private final int x;
    private final int y;
    private final Type type;

    public PieceFactory(Team team, int position, Type type) {
        this.team = team;
        this.x = position / 10;
        this.y = position % 10;
        this.type = type;
    }

    public PieceFactory(Team team, int x, int y, Type type) {
        this.team = team;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    @Override
    public Piece getInstance() {
        Piece piece;
        switch (type) {
            case QUEEN:
                piece = new Queen(team, x, y);
                break;
            case ROOK:
                piece = new Rook(team, x, y);
                break;
            case BISHOP:
                piece = new Bishop(team, x, y);
                break;
            case KNIGHT:
                piece = new Knight(team, x, y);
                break;
            default:
                throw new PieceNotFound();
// todo include pawn or king?  // piece = new Pawn(team, x, y);
        }
        return piece;
    }

//    public Piece getInstance(Piece piece){
////        this.team = piece.getTeam();
////        this.x = x;
////        this.y = y;
////        this.type = type;
////    }
//    }
}
