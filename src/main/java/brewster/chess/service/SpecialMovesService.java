package brewster.chess.service;

import brewster.chess.exception.PieceNotFound;
import brewster.chess.model.ChessGame;
import brewster.chess.model.Move;
import brewster.chess.model.constant.SpecialMove;
import brewster.chess.model.constant.Type;
import brewster.chess.model.piece.Pawn;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.PieceFactory;
import brewster.chess.model.piece.Square;
import brewster.chess.model.request.MoveRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class SpecialMovesService {
    private final CheckService checkService;

    public SpecialMovesService(CheckService checkService) {
        this.checkService = checkService;
    }


    public Map<Integer, SpecialMove> getSpecialMoves(Piece piece, ChessGame game, List<Integer> validMoves) {
        Map<Integer, SpecialMove> specialMoves = new HashMap<>();
        if (piece.getType() == Type.PAWN) {
            Pawn pawn = (Pawn) piece;
            if (pawn.canPromote()) {
                List<Integer> promotions = validMoves.stream().filter(square -> square % 10 == 1 || square % 10 == 8).collect(Collectors.toList());
                if (!promotions.isEmpty()) {
                    promotions.forEach(p -> specialMoves.put(p, SpecialMove.Promotion));
                }
            } else {
                Move lastMove = game.getMoves().get(game.getMoves().size() - 1);
                if (canPassant(piece, lastMove)) {
                    int diff = lastMove.getEnd() > lastMove.getStart() ? -1 : 1;
                    int passant = lastMove.getEnd() + diff;
                    log.info("last move {} diff {} and passant {}", lastMove.getEnd(), diff, passant);
                    validMoves.add(passant);
                    specialMoves.put(passant, SpecialMove.Passant);
                }
            }
        } else if (piece.getType() == Type.KING) {
            if (piece.getSquare().x == 5 && !game.isCheck()) {
                List<Integer> castles = eligibleCastles(piece, game);
                if (!castles.isEmpty()) {
                    castles.forEach(castle -> {
                        validMoves.add(castle);
                        specialMoves.put(castle, SpecialMove.Castle);
                    });
                }
            }
        }
        return specialMoves;
    }
    public void performSpecialMove(ChessGame game, MoveRequest request) {
        log.info("performing special move {}", request);
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

    private boolean canPassant(Piece piece, Move lastMove) {
        if (Math.abs(lastMove.getStart() - lastMove.getEnd()) == 2) {
            return Math.abs(piece.getSquare().intValue() - lastMove.getEnd()) == 10;
        }
        return false;
    }

    private List<Integer> eligibleCastles(Piece king, ChessGame game) {
        List<Integer> castles = new ArrayList<>();
        if (game.getMoves().stream().anyMatch(move -> move.getStart() == king.getSquare().intValue())) {
            return castles;
        }
        if (isClearToSide(-1, king, game)) {
            castles.add((king.getSquare().x - 2) * 10 + king.getSquare().y);
        }
        if (isClearToSide(1, king, game)) {
            castles.add((king.getSquare().x + 2) * 10 + king.getSquare().y);
        }
        return castles;
    }

    private boolean isClearToSide(int direction, Piece piece, ChessGame game) {
        int x = piece.getSquare().x + direction;
        int y = piece.getSquare().y;
        List<Square> occupiedSquares = game.getAllOccupiedSquares();
        while (x > 1 && x < 8) {
            Square square = new Square(x, y);
            if (occupiedSquares.contains(square) || checkService.isSquareUnderAttack(square, game.getFoesPieces(), occupiedSquares)) {
                return false;
            }
            x += direction;
        }
        return isRookInPosition(x, y, game);
    }

    private boolean isRookInPosition(int x, int y, ChessGame game) {
        Optional<Piece> rook = game.potentialPiece(game.getCurrentTeam(), x * 10 + y);
        return rook.isPresent() && rook.get().getType() == Type.ROOK;
    }

    private void performPassant(ChessGame game, MoveRequest request) {
        int enemyLocation = request.getEnd() / 10 * 10 + request.getStart() % 10;
        Piece foe = game.getPotentialFoe(enemyLocation).orElseThrow(PieceNotFound::new);
        game.getFoesPieces().remove(foe);
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

    private void performPromotion(ChessGame game, MoveRequest request) {
        log.info("performing promotion to {}", request.getPromotionType());
        Piece pawn = game.getOwnPiece(request.getEnd());
        List<Piece> pieces = game.getCurrentTeam();
        pieces.remove(pawn);
        pieces.add(new PieceFactory(pawn.getTeam(), request.getEnd(), request.getPromotionType()).getInstance());
    }

}
