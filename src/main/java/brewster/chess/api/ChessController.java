package brewster.chess.api;


import brewster.chess.model.User;
import brewster.chess.model.request.MoveRequest;
import brewster.chess.model.request.NewGameRequest;
import brewster.chess.model.request.RejoinRequest;
import brewster.chess.model.response.GameResponse;
import brewster.chess.model.response.NewGameResponse;
import brewster.chess.service.ChessGameService;
import brewster.chess.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping("/game")
@Slf4j
public class ChessController {
    public final UserService userService;
    public final ChessGameService gameService;

    public ChessController(UserService userService, ChessGameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }


//    @PostMapping
//    public NewGameResponse startNewGame(@RequestBody String name){
//        //todo sockets
////        return gameService.startGame(userService.findById(name).orElseThrow(UserNotFound::new));
//    }
    @PostMapping
    public NewGameResponse startLocalGame(@RequestBody NewGameRequest request){
        log.info("start new game - {}", request);
        User user1 = userService.getUser(request.getUser1());
        User user2 = userService.getUser(request.getUser2());

        return gameService.startGame(user1, user2);
    }

    @GetMapping("/rejoin")
    public GameResponse rejoinGame(@RequestBody RejoinRequest rejoinRequest){
        return gameService.rejoinGame(rejoinRequest.getGameId());
    }


//    @GetMapping("/{id}")
//    public Map<Integer, PieceMoves> getAllMoves(@PathVariable long id){
//        log.info("fetching all legal moves");
//        return gameService.getAllMoves(id);
//    }
    @PostMapping("/{id}")
    public GameResponse movePiece(@PathVariable long id, @RequestBody MoveRequest request) {
        log.info("moving piece - {}", request);
        if (request.getStart() == request.getEnd()) {
            throw new RuntimeException("Bad request");
        }
        return gameService.movePiece(id, request);
    }

    @PostMapping("/restart")
    public NewGameResponse restart(@RequestBody NewGameRequest request){
        log.info("the request is {}", request);
        User user1 = userService.getUser(request.getUser1());
        User user2 = userService.getUser(request.getUser2());

        return gameService.startGame(user1, user2);
    }

    @PostMapping("/{id}/draw")
    public GameResponse requestDraw(@PathVariable long id){
        log.info("requesting draw");
        return gameService.requestDraw(id);
    }
    @PostMapping("/{id}/forfeit")
    public GameResponse giveUp(@PathVariable long id){
        return gameService.requestDraw(id);
    }
}

