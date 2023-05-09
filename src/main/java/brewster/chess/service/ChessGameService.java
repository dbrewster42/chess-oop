package brewster.chess.service;

import brewster.chess.exception.GameNotFound;
import brewster.chess.exception.InvalidMoveException;
import brewster.chess.exception.PieceNotFound;
import brewster.chess.model.ChessGame;
import brewster.chess.model.User;
import brewster.chess.model.piece.Pawn;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.PieceFactory;
import brewster.chess.model.piece.Square;
import brewster.chess.model.request.MoveRequest;
import brewster.chess.model.request.PromotionRequest;
import brewster.chess.model.response.GameResponse;
import brewster.chess.model.response.NewGameResponse;
import brewster.chess.repository.GameRepository;
import brewster.chess.service.model.GamePiecesDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChessGameService {
    private final GameRepository repository;
    private final CheckService checkService;
    private final UserService userService;
    private final MoveMessageService moveMessageService;

    public ChessGameService(GameRepository repository, CheckService checkService, UserService userService, MoveMessageService moveMessageService) {
        this.repository = repository;
        this.checkService = checkService;
        this.userService = userService;
        this.moveMessageService = moveMessageService;
    }

    public NewGameResponse startGame(User user1, User user2) {
        return new NewGameResponse(repository.save(new ChessGame(user1, user2)));
    }

    public Map<Integer, List<Integer>> getAllMoves(long id) {
        ChessGame game = findGame(id);
        Map<Integer, List<Integer>> allMoves = new HashMap<>();
        for (Piece piece : game.getCurrentPlayer().getPieces()) {
            List<Integer> pieceMoves = getLegalMoves(game, piece.getLocation());
            if (!pieceMoves.isEmpty()) {
                allMoves.put(piece.getLocation(), pieceMoves);
            }
        }
        return allMoves;
    }
    private List<Integer> getLegalMoves(ChessGame game, int position) {
        return getPiece(game, position)
            .calculateLegalMoves(game.getAllOccupiedSquares(), game.getFoesPieces())
            .stream()
            .map(Square::intValue)
            .collect(Collectors.toList());
    }
    public List<Integer> getLegalMoves(long id, int position) {
        return getLegalMoves(findGame(id), position);
    }

    public GameResponse movePiece(long id, MoveRequest request) {
        return movePiece(findGameWithMoves(id), request);
    }
    GameResponse movePiece(ChessGame game, MoveRequest request) {
        GamePiecesDto dto = getGamePiecesDto(game);
        Piece piece = getPiece(game, request.getStart());
        piece.move(request.getEnd());

        if (checkService.isInCheckAfterMove(dto)) {
            piece.move(request.getStart());
            throw new InvalidMoveException(game.isCheck());
        }
        game.setCheck(false);
        Optional<Piece> potentialFoe = potentialPiece(game.getFoesPieces(), request.getEnd());
        potentialFoe.ifPresent(foe -> game.getFoesPieces().remove(foe));
        
        Optional.ofNullable(request.getSpecialMove()).ifPresent(s ->  performSpecialMove(game, request));

//        boolean isPromotion = isPromotion(piece); //todo will incorporate via selectPieces and choose with MoveRequest
//        if (isPromotion) {
//            return new PromotionResponse(game, request);
//        }
        if (checkService.didCheck(dto)){
            game.setCheck(true);
            if (checkService.didCheckMate(dto)){
                return checkMate(game);
            }
        }
        moveMessageService.addMove(game, piece.getType().name, request, potentialFoe);
        return endTurn(game);
    }

    private void performSpecialMove(ChessGame game, MoveRequest request) {
        switch (request.getSpecialMove()) {
            case Castle:
                performCastle(request);
                break;
            case Passant:
                performPassant(game, request);
                break;
            case Promotion:
                performPromotion(game, request);
        }
    }

    private void performPassant(ChessGame game, MoveRequest request) {
        int enemyLocation = request.getEnd() / 10 + request.getStart() % 10;
        Piece foe = getPiece(game, enemyLocation);
        game.getFoesPieces().remove(foe);
    }

    private void performCastle(MoveRequest request) {
    }

    public void performPromotion(ChessGame game, MoveRequest request) {
        List<Piece> pieces = game.getCurrentTeam();
        Pawn piece = (Pawn) getPiece(pieces, request.getEnd()); //todo pass in piece?
        pieces.remove(piece);
        pieces.add(new PieceFactory(piece.getTeam(), request.getEnd(), request.getPromotionType()).getInstance());
    }

    private boolean isPromotion(Piece piece) {
        if (piece instanceof Pawn) {
            return piece.getSquare().y == 1 || piece.getSquare().y == 8;
        }
        return false;
    }

    private GamePiecesDto getGamePiecesDto(ChessGame game){
        return GamePiecesDto.builder()
            .occupiedSquares(game.getAllOccupiedSquares())
            .friends(game.getCurrentTeam())
            .foes(game.getFoesPieces())
            .build();
    }

    public GameResponse implementPromotion(long id, PromotionRequest request) {
        ChessGame game = findGame(id);
        List<Piece> pieces = game.getCurrentTeam();
        Piece piece = getPiece(pieces, request.getOldPosition());
        pieces.remove(piece);
        pieces.add(new PieceFactory(piece.getTeam(), request.getNewPosition(), request.getType()).getInstance());
//        pieces.add(new Queen(piece.getTeam(), request.getNewPosition() / 10, request.getNewPosition() % 10));
        return endTurn(game);
    }

    public GameResponse requestDraw(long id) {
        ChessGame game = findGame(id);
        if (!game.isCheck() && checkService.isStaleMate(getGamePiecesDto(game))){
            //todo update user win totals with a draw. by calling user service?
            return draw(game);
        }
        //todo request other player for Draw
        return null;
    }

    private ChessGame findGame(long id){
        return repository.findById(id).orElseThrow(GameNotFound::new);
    }
    private ChessGame findGameWithMoves(long id){
        return repository.findGameWithMoves(id).orElseThrow(GameNotFound::new);
    }

    private GameResponse endTurn(ChessGame game) {
        repository.save(game.changeTurn());
        return new GameResponse(game);
    }
    private GameResponse checkMate(ChessGame game) {
        User winner = game.getCurrentPlayer().getUser().addWin();
        User loser = game.getOpponent().getUser().addLoss();
        endGame(game, winner, loser);
        return new GameResponse(winner.getName(), loser.getName());
    }
    private GameResponse draw(ChessGame game) {
        endGame(game, game.getCurrentPlayer().getUser(), game.getOpponent().getUser());
        return new GameResponse();
    }

    private void endGame(ChessGame game, User player1, User player2) {
        userService.save(player1);
        userService.save(player2);
        repository.delete(game);
    }

    private Piece getPiece(ChessGame game, int position) {
        return getPiece(game.getCurrentTeam(), position);
    }
    private Piece getPiece(List<Piece> pieces, int position) {
        return pieces.stream()
            .filter(piece -> piece.isAtPosition(position))
            .findAny().orElseThrow(PieceNotFound::new);
    }

    private Optional<Piece> potentialPiece(List<Piece> pieces, int position) {
        return pieces.stream()
            .filter(piece -> piece.isAtPosition(position))
            .findAny();
    }

//    public List<Piece> getFoesPieces(ChessGame game){
//        return getOpponent(game).getPieces();
//    }
//    public List<Piece> getCurrentTeam(ChessGame game){
//        return getCurrentPlayer(game).getPieces();
//    }
//    public Player getCurrentPlayer(ChessGame game){
//        return game.isWhitesTurn() ? game.getWhitePlayer() : game.getBlackPlayer();
//    }
//    public Player getOpponent(ChessGame game){
//        return game.isWhitesTurn() ? game.getBlackPlayer() : game.getWhitePlayer();
//    }
//
//    public List<Square> getAllSquares(ChessGame game){
//        return game.getAllPieces().stream()
//                .map(Piece::getSquare)
//                .collect(Collectors.toList());
//    }
////    public List<Spot> convertToSpots(Player player){
////        return player.getPieces().stream().map(Piece::getSpot).collect(Collectors.toList());
////    }
////    public boolean isOccupied(Game game, int location){
////        return getAllPieces(game).anyMatch(piece -> piece.isAtPosition(location));
////    }



//    void removeFoeIfCaptured(ChessGame game, int end) {
//        getFoesPieces(game).stream().filter(p -> p.isAtPosition(end)).findAny()
//                .ifPresent(foe -> getFoesPieces(game).remove(foe));
//    }
//
//    private boolean isAttack(ChessGame game, int end) {
//        return getFoesPieces(game).stream().anyMatch(p -> p.isAtPosition(end));
//    }
//    private Optional<Piece> isFoeCaptured(ChessGame game, int end) {
//        return getFoesPieces(game).stream().filter(p -> p.isAtPosition(end)).findAny();
//    }

}
