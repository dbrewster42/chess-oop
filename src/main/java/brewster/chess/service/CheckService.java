package brewster.chess.service;

import brewster.chess.model.piece.King;
import brewster.chess.model.piece.Knight;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.Square;
import brewster.chess.service.model.GamePiecesDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckService {


    public boolean isInCheckAfterMove(GamePiecesDto dto) {
        return isSquareUnderAttack(dto.getFriends().get(0).getSquare(), dto.getFoes(), dto.getOccupiedSquares());
    }
    public boolean didCheck(GamePiecesDto dto) {
        return isSquareDefended(dto.getFoes().get(0).getSquare(), dto);
    }
    public boolean didCheckMate(GamePiecesDto dto) {
        List<Square> kingsMoves = dto.getFoes().get(0).calculateLegalMoves(dto.getOccupiedSquares(), dto.getFriends());
        for (Square kingsMove : kingsMoves){
            if (isMoveOpen(kingsMove, dto)){
                return false;
            }
        }
        List<Piece> allAttackers = findAllAttackers(dto);
        if (allAttackers.size() > 1){
            return true;
        }
        return cannotBeTakenOrBlocked(dto, allAttackers.get(0));
    }

    private boolean isSquareDefended(Square square, GamePiecesDto dto){
        return isSquareUnderAttack(square, dto.getFriends(), dto.getOccupiedSquares());
    }

    private boolean isSquareUnderAttack(Square square, List<Piece> attackingTeam, List<Square> allSquares){
        for (Piece friend : attackingTeam){
            if (friend.isLegalAttack(square, allSquares)){
                return true;
            }
        }
        return false;
    }

    private boolean cannotBeTakenOrBlocked(GamePiecesDto dto, Piece attacker) {
        return canNotBeTaken(dto, attacker) && canNotBeBlocked(dto, attacker);
    }

    private List<Piece> findAllAttackers(GamePiecesDto dto) {
        Square enemyKingSquare = dto.getFoes().get(0).getSquare();
        return dto.getFriends().stream()
            .filter(friend -> friend.isLegalAttack(enemyKingSquare, dto.getOccupiedSquares()))
            .collect(Collectors.toList());
    }

    private boolean canNotBeTaken(GamePiecesDto dto, Piece attacker) {
        Square attackerSquare = attacker.getSquare();
        for (Piece foe : dto.getFoes()){
            if (foe.isLegalAttack(attackerSquare, dto.getOccupiedSquares())){
                if (foe instanceof King){
                    if (!isSquareDefended(attackerSquare, dto)) {
                        return false;
                    }
                } else {
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
            if (isSquareDefended(block, dto)){
                return false;
            }
        }
        return true;
    }

    private boolean isOneSquareAway(Square square, Piece attacker) {
        return Math.abs(attacker.getSquare().x - square.x) <= 1 && Math.abs(attacker.getSquare().y - square.y) <= 1;
    }

    private List<Square> getSquaresToBlock(GamePiecesDto dto, Piece attacker){
        Square enemyKingSquare = dto.getFoes().get(0).getSquare();
        Square attackerSquare = attacker.getSquare();

        int xDirection = getDirection(enemyKingSquare.x - attackerSquare.x);
        int yDirection = getDirection(enemyKingSquare.y - attackerSquare.y);

        return attacker.addMovesAlongLine(new ArrayList<>(), dto.getOccupiedSquares(), dto.getFoes(), xDirection, yDirection);
    }

    private int getDirection(int dif){
        if (dif > 1) { dif = 1; }
        else if (dif < -1) { dif = -1; }
        return dif;
    }

    private boolean isMoveOpen(Square kingsMove, GamePiecesDto dto){
        for (Piece friend : dto.getFriends()){
            if (!kingsMove.equals(friend.getSquare()) && friend.isLegalAttack(kingsMove, dto.getOccupiedSquares())){
                return false;
            }
        }
        return true;
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