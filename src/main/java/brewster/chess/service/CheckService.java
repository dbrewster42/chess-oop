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
        for (Piece friend : dto.getFoes()){
            if (friend.calculateLegalMoves(dto.getSpots(), dto.getFriends()).contains(kingsLocation)){
                return true;
            }
        }
        return false;
    }

    public boolean didCheck(GamePiecesDto dto) {
        Point kingsLocation = dto.getFoes().get(0).getSpot();
        for (Piece friend : dto.getFriends()){
            if (friend.calculateLegalMoves(dto.getSpots(), dto.getFoes()).contains(kingsLocation)){
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
        //1. canBeBlocked() find piece/s that are pressuring king
        //2. Piece - isLegalMove() for efficiency
        return true;
    }

    private boolean isMoveOpen(Point kingsMove, GamePiecesDto dto){
        dto.getFriends().get(6).calculateLegalMoves(dto.getSpots(), dto.getFoes());
        for (Piece friend : dto.getFriends()){
            if (friend.calculateLegalMoves(dto.getSpots(), dto.getFoes()).contains(kingsMove)){
                return false;
            }
        }
        return true;
    }

    public boolean isStaleMate(GamePiecesDto gamePiecesDto) {
        return false;
    }
}