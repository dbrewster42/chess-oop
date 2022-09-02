package brewster.chess.api;


import java.awt.Point;
import java.util.List;

import brewster.chess.exception.GameNotFound;
import brewster.chess.exception.UserNotFound;
import brewster.chess.model.Game;
import brewster.chess.model.User;
import brewster.chess.model.request.NewGameRequest;
import brewster.chess.model.request.PromotionRequest;
import brewster.chess.model.response.NewGameResponse;
import brewster.chess.model.response.PieceResponse;
import brewster.chess.repository.GameRepository;
import brewster.chess.repository.UserRepository;
import brewster.chess.service.GameService;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping("/game")
public class Controller {
    public final GameRepository gameRepository;
    public final UserRepository userRepository;

    public final GameService gameService;

    public Controller(GameRepository gameRepository, UserRepository userRepository, GameService gameService) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.gameService = gameService;
    }

    @PostMapping("/user")
    public List<String> createUser(@RequestBody String request){
//        game = Manager.createGame(request.getName1(), request.getName2());
//        board = Manager.getBoard(game.getId());
//        List<Response> returnValue = board.returnBoard();
//        Player player1= game.players[0];
//        Status status = game.getStatus();
//        StatusResponse statusResponse = new StatusResponse(status.isActive(), status.isCheck(), player1, game.getId());
//        returnValue.add(statusResponse);
//        return returnValue;
        return List.of("");
    }

//    @PostMapping
//    public List<String> startNewGame(@RequestBody String email){
//        return gameService.startGame();
//    }
    @PostMapping
    public NewGameResponse startNewLocalGame(@RequestBody NewGameRequest request){
        User user1 = userRepository.findById(request.getUser1()).orElseThrow(UserNotFound::new);
        User user2 = userRepository.findById(request.getUser2()).orElseThrow(UserNotFound::new);

        return gameService.startGame(user1, user2);
    }

    @GetMapping("/{id}")
    public List<Point> selectPiece(@PathVariable long id, int position){
        Game game = gameRepository.findById(id).orElseThrow(GameNotFound::new);
        return gameService.getLegalMoves(game, position);
    }

    @PostMapping("/{id}/promotion")
    public List<PieceResponse> selectPromotion(@PathVariable long id, @RequestBody PromotionRequest request){
        Game game = gameRepository.findById(id).orElseThrow(GameNotFound::new);
        return gameService.implementPromotion(game, request);
    }

//    @PostMapping("/restart")
//    public List<Response> restart(@PathVariable int id){
//        game = Manager.getGame(id);
//        String name1 = game.players[0].getName();
//        String name2 = game.players[1].getName();
//        game = Manager.createGame(name1, name2);
//        board = Manager.getBoard(game.getId());
//        List<Response> returnValue = board.returnBoard();
//        Player player1= game.players[0];
//        StatusResponse status = new StatusResponse(true, false, player1, game.getId());
//        returnValue.add(status);
//        return returnValue;
//    }
//
//    @PostMapping("/{id}")
//    public List<Response> makeMove(@PathVariable int id, @RequestBody BoardRequest boardRequest) {
//        Game game = Manager.getGame(id);
//        board = Manager.getBoard(game.getId());
//        StatusResponse status = game.run(boardRequest, board);
//        List<Response> returnValue = board.returnBoard();
//        returnValue.add(status);
//        return returnValue;
//    }
//
//    @PostMapping("/{id}/undo")
//    public List<Response> undo(@PathVariable int id){
//        Game game = Manager.getGame(id);
//        board = Manager.getBoard(game.getId());
//        StatusResponse status = game.undo(board);
//        List<Response> returnValue = board.returnBoard();
//        returnValue.add(status);
//        return returnValue;
//    }
//
//    @PostMapping("/{id}/end")
//    public StatusResponse endGame(@PathVariable int id, @RequestBody StatusRequest statusRequest){
//        Game game = Manager.getGame(id);
//        Status status = game.getStatus();
//        status.setActive(false);
//        if (statusRequest.isForfeit()){
//            StatusResponse statusResponse = new StatusResponse(statusRequest.getPlayerName() + " declares defeat! Game Over!");
//            return statusResponse;
//        }
//        StatusResponse statusResponse = new StatusResponse("We have a draw! Good Game!");
//        return statusResponse;
//    }
//
//    @GetMapping("/{id}/moves")
//    public MovesResponse displayMoves(@PathVariable int id){
//        ///must separate out moves lists
//        Game game = Manager.getGame(id);
//        MovesResponse movesResponse = new MovesResponse(game.returnMoveMessages());
//        return movesResponse;
//    }
//
////    @GetMapping("/pieces")
////    public PieceResponse displayPieces(Player player){
////        Player otherPlayer = game.getOtherTeam(player);
////        PieceResponse pieceResponse = new PieceResponse(player.getTeam(), otherPlayer.getTeam());
////        return pieceResponse;
////    }
}

