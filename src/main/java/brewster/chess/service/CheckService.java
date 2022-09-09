package brewster.chess.service;

import brewster.chess.piece.Piece;
import brewster.chess.service.model.GamePiecesDto;
import org.springframework.stereotype.Service;

import java.awt.Point;
import java.util.List;

@Service
public class CheckService {

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
        //todo add to see if piece can take or block
        //1. findAttackers() find piece/s pressuring king. if multiple then checkmate
        // or set attacker in didCheck, then see if piece can taken/blocked, then add that to allSpots and re-run didCheck
        //2. canBeTaken() if 1 piece
        //3. canBeBlocked
        return true;
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