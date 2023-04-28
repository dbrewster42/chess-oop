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


    public boolean didCheck(GamePiecesDto dto) {
        return isSpotDefended(dto, dto.getFoes().get(0).getSquare());
    }
    public boolean isInCheckAfterMove(GamePiecesDto dto) {
        return isSpotUnderAttack(dto.getFriends().get(0).getSquare(), dto.getFoes(), dto.getSquares());
    }
    private boolean isSpotDefended(GamePiecesDto dto, Square square){
        return isSpotUnderAttack(square, dto.getFriends(), dto.getSquares());
    }
    private boolean isSpotUnderAttack(Square square, List<Piece> attackingTeam, List<Square> allSquares){
        for (Piece friend : attackingTeam){
            if (friend.isLegalAttack(square, allSquares)){
                return true;
            }
        }
        return false;
    }

    public boolean didCheckMate(GamePiecesDto dto) {
        List<Square> kingsMoves = dto.getFoes().get(0).calculateLegalMoves(dto.getSquares(), dto.getFriends());
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

    private boolean cannotBeTakenOrBlocked(GamePiecesDto dto, Piece attacker) {
        return canNotBeTaken(dto, attacker) && canNotBeBlocked(dto, attacker);
    }

    private List<Piece> findAllAttackers(GamePiecesDto dto) {
        Square kingsLocation = dto.getFoes().get(0).getSquare();
        return dto.getFriends().stream()
            .filter(friend -> friend.isLegalAttack(kingsLocation, dto.getSquares()))
            .collect(Collectors.toList());
    }

    private boolean canNotBeTaken(GamePiecesDto dto, Piece attacker) {
        Square attackerSquare = attacker.getSquare();
        for (Piece foe : dto.getFoes()){
            if (foe.isLegalAttack(attackerSquare, dto.getSquares())){
                if (foe instanceof King){
                    if (!isSpotDefended(dto, attackerSquare)) {
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
        if (attacker instanceof Knight || isOneSpotAway(dto.getFoes().get(0).getSquare(), attacker)){
            return true;
        }
        for (Square block : getSpotsToBlocks(dto, attacker)){
            if (isSpotDefended(dto, block)){
                return false;
            }
        }
        return true;
    }

    private boolean isOneSpotAway(Square square, Piece attacker) {
        return Math.abs(attacker.getSquare().x - square.x) <= 1 && Math.abs(attacker.getSquare().y - square.y) <= 1;
    }

    private List<Square> getSpotsToBlocks(GamePiecesDto dto, Piece attacker){
        Square kingsLocation = dto.getFoes().get(0).getSquare();
        Square attackerLocation = attacker.getSquare();

        int xDirection = reduceToDirection(kingsLocation.x - attackerLocation.x);
        int yDirection = reduceToDirection(kingsLocation.y - attackerLocation.y);

        return attacker.addMovesAlongLine(new ArrayList<>(), dto.getSquares(), dto.getFoes(), xDirection, yDirection);
    }

    private int reduceToDirection(int dif){
        if (dif > 1) dif = 1;
        else if (dif < -1) dif = -1;
        return dif;
    }

    private boolean isMoveOpen(Square kingsMove, GamePiecesDto dto){
        for (Piece friend : dto.getFriends()){
            if (!kingsMove.equals(friend.getSquare()) && friend.isLegalAttack(kingsMove, dto.getSquares())){
                return false;
            }
        }
        return true;
    }

    public boolean isStaleMate(GamePiecesDto dto) {
        for (Piece friend : dto.getFriends()){
            for (Square square : friend.calculateLegalMoves(dto.getSquares(), dto.getFoes())){
                friend.move(square.x, square.y);
                if (!isInCheckAfterMove(dto)){
                    return false;
                }
            }
        }
        return true;
    }
}