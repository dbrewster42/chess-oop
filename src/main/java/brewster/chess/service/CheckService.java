package brewster.chess.service;

import brewster.chess.model.piece.King;
import brewster.chess.model.piece.Knight;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.Square;
import brewster.chess.service.model.GamePiecesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CheckService {

    public boolean isInCheckAfterMove(GamePiecesDto dto) {
        return isSquareUnderAttack(dto.getFriends().get(0).getSquare(), dto.getFoes(), dto.getOccupiedSquares());
    }
    public boolean didCheck(GamePiecesDto dto) {
        return isSquareUnderAttack(dto.getFoes().get(0).getSquare(), dto.getFriends(), dto.getOccupiedSquares());
//        return isSquareDefended(dto.getFoes().get(0).getSquare(), dto);
    }
    public boolean didCheckMate(GamePiecesDto dto) {
        List<Square> foeKingMoves = dto.getFoes().get(0).calculateLegalMoves(dto.getOccupiedSquares(), dto.getFriends());
        for (Square foeKingMove : foeKingMoves){
            if (isKingsMoveOpen(foeKingMove, dto)){
                log.info("King can move out of check to {}", foeKingMove);
                return false;
            }
        }
        List<Piece> allAttackers = findAllAttackers(dto);
        if (allAttackers.size() > 1){
            return true;
        }
        return cannotBeTakenOrBlocked(dto, allAttackers.get(0));
    }

//    private boolean isSquareDefended(Square square, GamePiecesDto dto){
//        return isSquareUnderAttack(square, dto.getFriends(), dto.getOccupiedSquares());
//    }

    public boolean isSquareUnderAttack(Square square, List<Piece> attackingTeam, List<Square> occupiedSquares){
        for (Piece attacker : attackingTeam){
            if (attacker.isLegalAttack(square, occupiedSquares)){
                log.info("{} can reach this square {}", attacker, square);
                return true;
            }
        }
        return false;
    }

    private List<Piece> findAllAttackers(GamePiecesDto dto) {
        Square enemyKingSquare = dto.getFoes().get(0).getSquare();
        return dto.getFriends().stream()
            .filter(friend -> friend.isLegalAttack(enemyKingSquare, dto.getOccupiedSquares()))
            .collect(Collectors.toList());
    }

    private boolean cannotBeTakenOrBlocked(GamePiecesDto dto, Piece attacker) {
        return canNotBeTaken(dto, attacker) && canNotBeBlocked(dto, attacker);
    }

    private boolean canNotBeTaken(GamePiecesDto dto, Piece attacker) {
        Square attackerSquare = attacker.getSquare();
        for (Piece foe : dto.getFoes()){
            if (foe.isLegalAttack(attackerSquare, dto.getOccupiedSquares())){
                if (foe instanceof King){
                    if (!isSquareUnderAttack(attackerSquare, dto.getFriends(), dto.getOccupiedSquares())) {
//                    if (!isSquareDefended(attackerSquare, dto)) {
                        log.info("can be taken by the king at {}", attackerSquare);
                        return false;
                    }
                } else {
                    log.info("can be taken at {}", attackerSquare);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canNotBeBlocked(GamePiecesDto dto, Piece attacker) {
        if (attacker instanceof Knight || isOneSquareAway(dto.getFoes().get(0).getSquare(), attacker)){
            return true;
        }
        for (Square block : getSquaresToBlock(dto, attacker)){
            for (Piece defender : dto.getFoes()){
                if (!(defender instanceof King) && defender.isLegalBlock(block, dto.getOccupiedSquares())){
                    log.info("{} can reach this blocking square {}", defender, block);
                    return false;
                }
            }
//            isLegalBlock
//            if (isSquareUnderAttack(block, dto.getFoes(), dto.getOccupiedSquares(), true)) {
////            if (isSquareDefended(block, dto)){
//                log.info("can be blocked at {}", block);
//                return false;
//            }
        }
        return true;
    }

    private boolean isOneSquareAway(Square square, Piece attacker) {
        return Math.abs(attacker.getSquare().x - square.x) <= 1 && Math.abs(attacker.getSquare().y - square.y) <= 1;
    }

    private List<Square> getSquaresToBlock(GamePiecesDto dto, Piece attacker){
        Square enemyKingSquare = dto.getFoes().get(0).getSquare();
        Square attackerSquare = attacker.getSquare();

//        int xDirection = getDirection(attackerSquare.x - enemyKing.getSquare().x);
//        int yDirection = getDirection(attackerSquare.y - enemyKing.getSquare().y);
//
//        return enemyKing.addMovesAlongLine(new ArrayList<>(), dto.getOccupiedSquares(), dto.getFoes(), xDirection, yDirection);
        int xDirection = getDirection(enemyKingSquare.x - attackerSquare.x);
        int yDirection = getDirection(enemyKingSquare.y - attackerSquare.y);

        return attacker.addMovesAlongLine(new ArrayList<>(), dto.getOccupiedSquares(), dto.getFoes(), xDirection, yDirection, false);
    }

    private int getDirection(int dif){
        if (dif > 1) { dif = 1; }
        else if (dif < -1) { dif = -1; }
        return dif;
    }

    private boolean isKingsMoveOpen(Square kingsMove, GamePiecesDto dto){
        for (Piece friend : dto.getFriends()){
            //todo why does it matter if a piece is there?  -          if (!kingsMove.equals(friend.getSquare()) && friend.isLegalAttack(kingsMove, dto.getOccupiedSquares())){
            if (friend.isLegalAttack(kingsMove, getOccupiedSquaresWithoutFoeKing(dto))){
                return false;
            }
        }
        return true;
    }

    private List<Square> getOccupiedSquaresWithoutFoeKing(GamePiecesDto dto) {
        List<Square> occupiedSquares = dto.getOccupiedSquares();
        occupiedSquares.remove(dto.getFoes().get(0).getSquare());
        return occupiedSquares;
    }
    private List<Square> getOccupiedSquaresWithoutSquare(Square square, GamePiecesDto dto) {
        List<Square> occupiedSquares = dto.getOccupiedSquares();
        occupiedSquares.remove(square);
        return occupiedSquares;
    }
    private List<Square> getOccupiedSquaresWithoutPiece(Piece piece, GamePiecesDto dto) {
        List<Square> occupiedSquares = dto.getOccupiedSquares();
        occupiedSquares.remove(piece.getSquare());
        return occupiedSquares;
    }

    public boolean isStaleMate(GamePiecesDto dto) {
        for (Piece friend : dto.getFriends()){
            for (Square square : friend.calculateLegalMoves(dto.getOccupiedSquares(), dto.getFoes())){
                int startPosition = friend.getSquare().intValue();
                friend.move(square.intValue());
                boolean isValidMove = !isInCheckAfterMove(dto);
                friend.move(startPosition);
                if (isValidMove){
                    return false;
                }
            }
        }
        return true;
    }
}