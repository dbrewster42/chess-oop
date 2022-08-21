package brewster.chess.service;

import brewster.chess.model.Piece;
import brewster.chess.model.Queen;
import brewster.chess.model.constant.Team;
import org.springframework.stereotype.Service;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class QueenService implements PieceService<Queen> {
    @Override
    public List<Point> calculatePotentialMoves(Queen piece, Stream<Piece> allPieces) {
        List<Point> potentialMoves = new ArrayList<>();
//        int x = piece.getX();
//        int y = piece.getY();
//        while (x > 0){
//            x--;
//            if (!isOccupied(x, y, allPieces)){
//                potentialMoves.add(new Point(x, y));
//            } else {
//                if (!isTeammate(piece, allPieces, x, y)){
//                    potentialMoves.add(new Point(x, y));
//                }
//                break;
//            }
//        }
//        x = piece.getX();
////        while (isOccupied())
//        if (isOccupied(x, ++y, allPieces)){
//            return List.of();
//        }
        addUpAndDownMoves(piece, allPieces, potentialMoves);
        addDiagonalMoves(piece, allPieces, potentialMoves);
        return potentialMoves;
    }

    private void addDiagonalMoves(Queen piece, Stream<Piece> allPieces, List<Point> moves){
        addMovesAlongLine(piece, allPieces, moves, -1, -1);
        addMovesAlongLine(piece, allPieces, moves, 1, 1);
        addMovesAlongLine(piece, allPieces, moves, 1, -1);
        addMovesAlongLine(piece, allPieces, moves, -1, 1);
    }
    private void addUpAndDownMoves(Queen piece, Stream<Piece> allPieces, List<Point> moves) {
//        addVerticleMoves(piece, allPieces, moves, -1);
//        addVerticleMoves(piece, allPieces, moves, 1);
        addMovesAlongLine(piece, allPieces, moves, -1, 0);
        addMovesAlongLine(piece, allPieces, moves, 1, 0);
        addMovesAlongLine(piece, allPieces, moves, 0, -1);
        addMovesAlongLine(piece, allPieces, moves, 0, 1);
    }
//    private void addVerticleMoves(Queen piece, Stream<Piece> allPieces, List<Point> moves, int direction) {
//        int x = piece.getX();
//        int y = piece.getY();
//        while (x > 0 && x < 9){
//            x += direction;
//            if (!isOccupied(x, y, allPieces)){
//                moves.add(new Point(x, y));
//            } else {
//                if (!isTeammate(piece, allPieces, x, y)){
//                    moves.add(new Point(x, y));
//                }
//                break;
//            }
//        }
//    }//todo add for horizontal too?

//    private void addMovesAlongLine(Queen piece, Stream<Piece> allPieces, List<Point> moves, int xDirection, int yDirection) {
//        int x = piece.getX();
//        int y = piece.getY();
//        while (x > 0 && x < 9 && y > 0 && y < 9){
//            x += xDirection;
//            y += yDirection;
//            if (!isOccupied(x, y, allPieces)){
//                moves.add(new Point(x, y));
//            } else {
//                if (!isTeammate(piece, allPieces, x, y)){
//                    moves.add(new Point(x, y));
//                }
//                break;
//            }
//        }
//    }

//    private Point
//    private boolean isTeammate(Queen piece, Stream<Piece> allPieces, int x, int y){
////        Team team = allPieces.filter(p -> p.getX() == x && p.getY() == y).findAny().map(Piece::getTeam).orElseThrow();
////        return team.equals(piece.getTeam());
//
////        Piece destination = allPieces.filter(p -> p.getX() == x && p.getY() == y).findAny().orElseThrow();
////        return destination.getTeam().equals(piece.getTeam());
//       return allPieces.filter(p -> p.getX() == x && p.getY() == y).findAny().map(Piece::getTeam).equals(piece.getTeam());
//    }
}
