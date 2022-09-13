package brewster.chess.service;

import brewster.chess.piece.King;
import brewster.chess.piece.Knight;
import brewster.chess.piece.Piece;
import brewster.chess.service.model.GamePiecesDto;
import org.springframework.stereotype.Service;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckService {
    private Piece attacker;


    public boolean didCheck(GamePiecesDto dto) {
//        return isSpotUnderAttack(dto.getFoes().get(0).getSpot(), dto.getFriends(), dto.getSpots());
        return isSpotDefended(dto, dto.getFoes().get(0).getSpot());
    }
    public boolean isInCheckAfterMove(GamePiecesDto dto) { //todo
        return isSpotUnderAttack(dto.getFriends().get(0).getSpot(), dto.getFoes(), dto.getSpots());
    }

    private boolean isSpotDefended(GamePiecesDto dto, Point spot){
        return isSpotUnderAttack(spot, dto.getFriends(), dto.getSpots());
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
        if (isMultipleAttackers(dto)){
            return true;
        }

        return !canBeTaken(dto) || !canBeBlocked(dto);
    }

    private List<Piece> findAllAttackers(GamePiecesDto dto) {
        Point kingsLocation = dto.getFoes().get(0).getSpot();
        return dto.getFriends().stream()
                .filter(friend -> friend.isLegalAttack(kingsLocation, dto.getSpots()))
                .collect(Collectors.toList());
//        List<Piece> allAttackers = new ArrayList<>();
//        for (Piece friend : dto.getFriends()){
//            if (friend.isLegalAttack(kingsLocation, dto.getSpots())){
//                allAttackers.add(friend);
//            }
//        }
//        return allAttackers;
    }

    private boolean isMultipleAttackers(GamePiecesDto dto){
        int allAttackers = 0;
        Point kingsLocation = dto.getFoes().get(0).getSpot();

        for (Piece friend : dto.getFriends()){
            if (friend.isLegalAttack(kingsLocation, dto.getSpots())){
                attacker = friend;
                allAttackers++;
            }
        }
        return allAttackers > 1;
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


    private boolean canBeBlocked(GamePiecesDto dto) {
        if (attacker instanceof Knight || isOneSpotAway(dto.getFoes().get(0).getSpot())){
            return false;
        }
        for (Point block : getSpotsToBlocks(dto)){
            if (isSpotDefended(dto, block)){
                return true;
            }
        }
        return false;
    }

    private boolean isOneSpotAway(Point spot) {
        return Math.abs(attacker.getSpot().x - spot.x) <= 1 && Math.abs(attacker.getSpot().y - spot.y) <= 1;
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