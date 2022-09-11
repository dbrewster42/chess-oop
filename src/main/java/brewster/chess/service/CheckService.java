package brewster.chess.service;

import brewster.chess.piece.Bishop;
import brewster.chess.piece.King;
import brewster.chess.piece.Piece;
import brewster.chess.piece.Queen;
import brewster.chess.piece.Rook;
import brewster.chess.service.model.GamePiecesDto;
import org.springframework.stereotype.Service;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckService {
    private Piece attacker;

    public boolean isInCheckAfterMove(GamePiecesDto dto) {
        Point kingsLocation = dto.getFriends().get(0).getSpot();
        for (Piece foe : dto.getFoes()){
            if (foe.isLegalAttack(kingsLocation, dto.getSpots())){
                return true;
            }
        }
        return false;
    }

    public boolean didCheck(GamePiecesDto dto) {
        Point kingsLocation = dto.getFoes().get(0).getSpot();
        for (Piece friend : dto.getFriends()){
            if (friend.isLegalAttack(kingsLocation, dto.getSpots())){
                attacker = friend;
                return true;
            }
        }
        return false;
    }
    public boolean isInCheckAfterMove2(GamePiecesDto dto) { //todo
        return isSpotUnderAttack(dto.getFriends().get(0).getSpot(), dto.getFoes(), dto.getSpots());
    }

    private boolean isSpotUnderAttack(Point spot, List<Piece> attackingTeam, List<Point> allSpots){
        for (Piece friend : attackingTeam){
            if (friend.isLegalAttack(spot, allSpots)){
                return true;
            }
        }
        return false;
    }

    public boolean didCheckMate(GamePiecesDto dto) {
        List<Point> kingsMoves = dto.getFoes().get(0).calculateLegalMoves(dto.getSpots(), dto.getFriends());
        for (Point kingsMove : kingsMoves){
            if (isMoveOpen(kingsMove, dto)){
                return false;
            }
        }
//        if (canBeTaken(dto)){
//            return false;
//        } else return !canBeBlocked(dto);
        return !canBeTaken(dto) || !canBeBlocked(dto);
        //todo add to see if piece can take or block
        //1. findAttackers() find piece/s pressuring king. if multiple then checkmate
        // or set attacker in didCheck, then see if piece can taken/blocked, then add that to allSpots and re-run didCheck
        //2. canBeTaken() if 1 piece
        //3. canBeBlocked
    }

    private boolean canBeTaken(GamePiecesDto dto) {
        Point attackerSpot = attacker.getSpot();
        for (Piece foe : dto.getFoes()){
            if (foe.isLegalAttack(attackerSpot, dto.getSpots())){
                if (foe instanceof King){
                    if (!isSpotDefended(dto, attackerSpot)) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSpotDefended(GamePiecesDto dto, Point spot){
        for (Piece friend : dto.getFriends()) {
            if (friend.isLegalAttack(spot, dto.getSpots())) {
               return true;
            }
        }
        return false;
    }

    private boolean canBeBlocked(GamePiecesDto dto) {
        if (attacker instanceof Queen || attacker instanceof Rook || attacker instanceof Bishop){
            List<Point> blocks = getSpotsToBlocks(dto);
        }
        return false;
    }

    private List<Point> getSpotsToBlocks(GamePiecesDto dto){
        Point kingsLocation = dto.getFoes().get(0).getSpot();
        Point attackerLocation = attacker.getSpot();

        int xDirection = reduceToDirection(kingsLocation.x - attackerLocation.x);
        int yDirection = reduceToDirection(kingsLocation.y - attackerLocation.y);

        return attacker.addMovesAlongLine(new ArrayList<>(), dto.getSpots(), dto.getFoes(), xDirection, yDirection);
    }

    private int reduceToDirection(int dif){
        if (dif > 1) dif = 1;
        else if (dif < -1) dif = -1;
        return dif;
    }

    private boolean isMoveOpen(Point kingsMove, GamePiecesDto dto){
        for (Piece friend : dto.getFriends()){
            if (!kingsMove.equals(friend.getSpot()) && friend.isLegalAttack(kingsMove, dto.getSpots())){
                return false;
            }
        }
        return true;
    }

    public boolean isStaleMate(GamePiecesDto gamePiecesDto) {
        return false;
    }
}