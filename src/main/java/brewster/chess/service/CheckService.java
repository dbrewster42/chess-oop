package brewster.chess.service;

import brewster.chess.model.request.MoveRequest;
import brewster.chess.piece.Piece;
import brewster.chess.service.model.GamePiecesDto;

import java.awt.Point;
import java.util.List;

public class CheckService {

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
        List<Point> kingsMoves = dto.getFoes().get(0).calculateLegalMoves(dto.getSpots(), dto.getFoes());
        for (Point kingsMove : kingsMoves){
            if (isMoveOpen(kingsMove, dto)){
                return false;
            }
        }
        return true;
    }
    private boolean isMoveOpen(Point kingsMove, GamePiecesDto dto){
        for (Piece friend : dto.getFriends()){
            if (friend.calculateLegalMoves(dto.getSpots(), dto.getFoes()).contains(kingsMove)){
                return false;
            }
        }
        return true;
    }

    public boolean didDefeatCheck(GamePiecesDto dto) {
        return false;
    }
    //can efficiently prevent moving into check by seeing if old position was on a line with the king
    public boolean movedIntoCheck(GamePiecesDto dto, MoveRequest request) {
        return false;
    }

    public boolean isStaleMate(GamePiecesDto gamePiecesDto) {
        return false;
    }
}