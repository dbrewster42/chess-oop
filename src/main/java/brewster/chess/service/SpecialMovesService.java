package brewster.chess.service;

import brewster.chess.model.ChessGame;
import brewster.chess.model.piece.Pawn;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.PieceFactory;
import brewster.chess.model.request.MoveRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpecialMovesService {
    public void performSpecialMove(ChessGame game, MoveRequest request) { //todo error handling?
        switch (request.getSpecialMove()) {
            case Castle:
                performCastle(game, request);
                break;
            case Passant:
                performPassant(game, request);
                break;
            case Promotion:
                performPromotion(game, request);
                break;
        }
    }

    private void performPassant(ChessGame game, MoveRequest request) {
        int enemyLocation = request.getEnd() / 10 + request.getStart() % 10;
        game.getPotentialFoe(enemyLocation)
            .ifPresent(foe -> game.getFoesPieces().remove(foe));
//        Piece foe = game.getPotentialFoe(enemyLocation);
//        game.getFoesPieces().remove(foe);
    }

    private void performCastle(ChessGame game, MoveRequest request) {
        int x = request.getEnd() / 10;
        int y = request.getEnd() % 10;
        if (x == 3) {
            Piece rook = game.getOwnPiece(10 + y);
            rook.move(4, y);
        } else if (x == 7)  {
            Piece rook = game.getOwnPiece(80 + y);
            rook.move(6, y);
        }
    }

    public void performPromotion(ChessGame game, MoveRequest request) {
        Piece pawn = game.getOwnPiece(request.getEnd());
        List<Piece> pieces = game.getCurrentTeam();
        pieces.remove(pawn);
        pieces.add(new PieceFactory(pawn.getTeam(), request.getEnd(), request.getPromotionType()).getInstance());
    }

}
