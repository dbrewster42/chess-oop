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
        this.type = type == null ? Type.QUEEN : type;
    }

    @Override
    public Piece getInstance() {
        switch (type) {
            case ROOK:
                return new Rook(team, x, y);
            case BISHOP:
                return new Bishop(team, x, y);
            case KNIGHT:
                return new Knight(team, x, y);
            default:
                return new Queen(team, x, y);
        }
    }

}
