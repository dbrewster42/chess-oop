package brewster.chess.mother;

import brewster.chess.model.constant.Team;
import brewster.chess.model.piece.King;
import brewster.chess.model.piece.Knight;
import brewster.chess.model.piece.Pawn;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.Queen;
import brewster.chess.service.model.GamePiecesDto;

import brewster.chess.model.piece.Spot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static brewster.chess.model.constant.Team.BLACK;
import static brewster.chess.model.constant.Team.WHITE;

public class PieceMother {

    public static List<Spot> getSpotsForGivenPieces(List<Piece> friends, List<Piece> foes){
        return Stream.concat(friends.stream(), foes.stream()).map(Piece::getSpot).collect(Collectors.toList());
    }

    public static List<Piece> getAllPieces() {
        return Stream.of(getWhiteKing(), getBlackKing(), getWhitePawn2(), getBlackPawn2(), getWhiteQueen(), getBlackQueen()).collect(Collectors.toList());
    }

    public static List<Piece> getWhitePieces() {
        return Stream.of(getWhiteKing(), getWhitePawn(), getWhitePawn2(), getWhiteQueen(), getWhiteBishop(), getWhiteKnight()).collect(Collectors.toList());
    }
    public static List<Piece> getBlackPieces() {
        return Stream.of(getBlackKing(), getBlackPawn(), getBlackPawn2(), getBlackQueen(), getBlackRook()).collect(Collectors.toList());
    }

    public static GamePiecesDto adjustDto(GamePiecesDto dto, Piece newFriend) {
        dto.getFriends().add(newFriend);
        dto.getSpots().add(newFriend.getSpot());
        return dto;
    }

    public static GamePiecesDto createKingQueenDto() {
        List<Piece> friends = new ArrayList<>(Arrays.asList(getKing(WHITE, 5, 7), getWhiteQueen()));
        List<Piece> foes = new ArrayList<>(Arrays.asList(getKing(BLACK, 7, 8), getQueen(BLACK, 8, 6)));
        return GamePiecesDto.builder()
            .spots(getSpotsForGivenPieces(friends, foes))
            .friends(friends)
            .foes(foes)
            .build();

    }

    public static GamePiecesDto createStalemateDto() {
        List<Piece> friends = new ArrayList<>(Arrays.asList(getKing(BLACK, 7, 8)));
        List<Piece> foes = new ArrayList<>(Arrays.asList(getKing(WHITE, 5, 7), getQueen(WHITE, 8, 6)));
        return GamePiecesDto.builder()
            .spots(getSpotsForGivenPieces(friends, foes))
            .friends(friends)
            .foes(foes)
            .build();
    }

    public static List<Piece> getFoes(){
        return Stream.of(getBlackKing(), getBlackPawn(), getBlackQueen()).collect(Collectors.toList());
    }

    public static List<Spot> getSpotsForKing(){
        return Stream.of(new Spot(5, 8), new Spot(8, 2), new Spot(8, 7)).collect(Collectors.toList());
    }

    public static List<Spot> getSpots2(){
        return Stream.of(new Spot(4, 1), new Spot(5, 1), new Spot(5, 8), new Spot(8, 2), new Spot(8, 7), new Spot(4, 8)).collect(Collectors.toList());
    }

    public static List<Spot> getSpots3(){
        return Stream.of(new Spot(4, 1), new Spot(5, 1), new Spot(5, 8), new Spot(5, 2), new Spot(5, 7), new Spot(4, 8)).collect(Collectors.toList());
    }

    public static Piece getWhiteKing(){
        return new King(WHITE, 5, 1);
    }
    public static Piece getKing(Team team, int x, int y){
        return new King(team, x, y);
    }
    public static Piece getQueen(Team team, int x, int y){
        return new Queen(team, x, y);
    }

    public static Piece getBlackKing(){
        return new King(BLACK, 5, 8);
    }

    public static Piece getWhitePawn(){
        return new Pawn(WHITE, 8, 2);
    }

    public static Piece getWhitePawn2(){
        return new Pawn(WHITE, 5, 2);
    }

    public static Piece getBlackPawn(){
        return new Pawn(BLACK, 8, 7);
    }
    public static Piece getBlackPawn2(){
        return new Pawn(BLACK, 5, 7);
    }

    public static Piece getBlackRook(){
        return new Pawn(BLACK, 8, 8);
    }

    public static Piece getWhiteBishop(){
        return new Pawn(WHITE, 3, 1);
    }

    public static Piece getEnterprisingBlackPawn(){
        return new Pawn(BLACK, 4, 2);
    }

    public static Piece getWhiteQueen(){
        return new Queen(WHITE, 4, 1);
    }

    public static Piece getBlackQueen(){
        return new Queen(BLACK, 4, 8);
    }

    public static Piece getWhiteKnight() { return new Knight(WHITE, 2, 1);
    }
}
