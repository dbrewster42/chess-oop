package brewster.chess.api;


import brewster.chess.exception.GameNotFound;
import brewster.chess.exception.UserNotFound;
import brewster.chess.model.Game;
import brewster.chess.model.User;
import brewster.chess.model.request.MoveRequest;
import brewster.chess.model.request.NewGameRequest;
import brewster.chess.model.request.PromotionRequest;
import brewster.chess.model.request.UserRequest;
import brewster.chess.model.response.GameResponse;
import brewster.chess.model.response.NewGameResponse;
import brewster.chess.repository.GameRepository;
import brewster.chess.repository.UserRepository;
import brewster.chess.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping("/game")
@Slf4j
public class Controller {
    public final UserRepository userRepository;
    public final GameService gameService;

    public Controller(UserRepository userRepository, GameService gameService) {
        this.userRepository = userRepository;
        this.gameService = gameService;
    }

    @PostMapping("/user")
    public String createUser(@RequestBody UserRequest request){
        log.info("new user - {}", request);
        User user = userRepository.save(new User(request.getName(), request.getEmail()));
//        if (isValid)
        return user.getName() + " has been saved in the db";
    }
    @GetMapping("/user")
    public String login(@RequestBody UserRequest request){
        log.info("login - {}", request);
        User user = userRepository.findById(request.getName()).orElseThrow(UserNotFound::new);
//        if (isValid)
        return user.getName() + " has been retrieved from the db";
    }

//    @PostMapping
//    public NewGameResponse startNewGame(@RequestBody String name){
//        //todo sockets
////        return gameService.startGame(userRepository.findById(name).orElseThrow(UserNotFound::new));
//    }
    @PostMapping
    public NewGameResponse startLocalGame(@RequestBody NewGameRequest request){
        log.info("start new game - {}", request);
        User user1 = userRepository.findById(request.getUser1()).orElseThrow(UserNotFound::new);
        User user2 = userRepository.findById(request.getUser2()).orElseThrow(UserNotFound::new);

        return gameService.startGame(user1, user2);
    }

    @GetMapping("/{id}/{position}")
    public List<Integer> selectPiece(@PathVariable long id, @PathVariable int position){
        log.info("selecting piece - {}", position);
        return gameService.getLegalMoves(id, position);
    }
    @GetMapping("/{id}/validMoves")
    public Map<Integer, List<Integer>> getLegalMoves(@PathVariable long id){
        log.info("fetching all legal moves");
        return gameService.getAllMoves(id);
    }

    @PostMapping("/{id}")
    public GameResponse movePiece(@PathVariable long id, @RequestBody MoveRequest request) {
        log.info("moving piece - {}", request);
        return gameService.movePiece(id, request);
    }

    @PostMapping("/restart")
    public NewGameResponse restart(@RequestBody NewGameRequest request){
        User user1 = userRepository.findById(request.getUser1()).orElseThrow(UserNotFound::new);
        User user2 = userRepository.findById(request.getUser2()).orElseThrow(UserNotFound::new);

        return gameService.startGame(user1, user2);
    }

    @GetMapping("/{id}/draw")
    public GameResponse requestDraw(@PathVariable long id){
        return gameService.requestDraw(id);
    }
    @PostMapping("/{id}/promotion")
    public GameResponse selectPromotion(@PathVariable long id, @RequestBody PromotionRequest request){
        return gameService.implementPromotion(id, request);
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

