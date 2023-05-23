package brewster.chess.service;

import brewster.chess.exception.GameNotFound;
import brewster.chess.exception.InvalidMoveException;
import brewster.chess.model.ChessGame;
import brewster.chess.model.User;
import brewster.chess.model.piece.Pawn;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.piece.Square;
import brewster.chess.model.request.MoveRequest;
import brewster.chess.model.response.GameResponse;
import brewster.chess.model.response.NewGameResponse;
import brewster.chess.model.response.PieceMoves;
import brewster.chess.repository.GameRepository;
import brewster.chess.service.model.GamePiecesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static brewster.chess.util.ImageMatch.getPiecesMap;

@Service
@Slf4j
public class ChessGameService {
    private final GameRepository repository;
    private final CheckService checkService;
    private final SpecialMovesService specialMovesService;
    private final UserService userService;
    private final MoveMessageService moveMessageService;

    public ChessGameService(GameRepository repository, CheckService checkService, SpecialMovesService specialMovesService, UserService userService, MoveMessageService moveMessageService) {
        this.repository = repository;
        this.checkService = checkService;
        this.specialMovesService = specialMovesService;
        this.userService = userService;
        this.moveMessageService = moveMessageService;
    }

    public NewGameResponse startGame(User user1, User user2) {
        ChessGame newGame = repository.save(new ChessGame(user1, user2));
        return new NewGameResponse(newGame, getAllMoves(newGame));
    }

//    public Map<Integer, PieceMoves> getAllMoves(long id) {
//        return getAllMoves(findGame(id));
//    }
    public Map<Integer, String> getPieces(long id) {
        ChessGame game = findGame(id);
        return getPiecesMap(game);
    }
    private Map<Integer, PieceMoves> getAllMoves(ChessGame game) {
        Map<Integer, PieceMoves> allMoves = new HashMap<>();
        for (Piece piece : game.getCurrentPlayer().getPieces()) {
            List<Integer> standardMoves = getLegalMoves(game, piece.getLocation());
            //todo add passantCheck. Probably more efficient if this is 2nd check with the 1st being a 2 space pawn move
            if (!standardMoves.isEmpty()) {
                //todo castle + promotion
                PieceMoves pieceMoves = new PieceMoves(standardMoves);
                allMoves.put(piece.getLocation(), pieceMoves);
            }
        }
        log.info("all moves - {}", allMoves);
        return allMoves;
    }
    private List<Integer> getLegalMoves(ChessGame game, int position) {
        return game.getOwnPiece(position)
            .calculateLegalMoves(game.getAllOccupiedSquares(), game.getFoesPieces())
            .stream()
            .map(Square::intValue)
            .collect(Collectors.toList());
    }
    public List<Integer> getLegalMoves(long id, int position) {
        return getLegalMoves(findGame(id), position);
    }

    public GameResponse movePiece(long id, MoveRequest request) {
        ChessGame game = findGameWithMoves(id);
        Piece piece = game.getOwnPiece(request.getStart());
        GamePiecesDto dto = getGamePiecesDto(game);


        if (!piece.isLegalAttack(new Square(request.getEnd()), dto.getOccupiedSquares())) {
            throw new InvalidMoveException("Illegal move");
        }
        piece.move(request.getEnd());
        Optional<Piece> potentialFoe = game.getPotentialFoe(request.getEnd());
        potentialFoe.ifPresent(foe -> game.getFoesPieces().remove(foe));

        if (checkService.isInCheckAfterMove(dto)) {
            log.info("check was not defeated");
            piece.move(request.getStart());
            potentialFoe.ifPresent(foe -> game.getFoesPieces().add(foe));
            throw new InvalidMoveException(game.isCheck());
        }
        game.setCheck(false);

        Optional.ofNullable(request.getSpecialMove())
            .ifPresent(s ->  specialMovesService.performSpecialMove(game, request));

        if (checkService.didCheck(dto)){
            game.setCheck(true);
            if (checkService.didCheckMate(dto)){
                return checkMate(game);
            }
        }
        moveMessageService.addMove(game, piece.getType(), request, potentialFoe);
        return endTurn(game);
    }


    private GamePiecesDto getGamePiecesDto(ChessGame game){
        return GamePiecesDto.builder()
            .occupiedSquares(game.getAllOccupiedSquares())
            .friends(game.getCurrentTeam())
            .foes(game.getFoesPieces())
            .build();
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
        return new GameResponse(game, getAllMoves(game));
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

}
