package brewster.chess.api;

import brewster.chess.model.ChessGame;
import brewster.chess.model.User;
import brewster.chess.model.request.MoveRequest;
import brewster.chess.model.request.NewGameRequest;
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

import java.util.List;


@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping("/chess")
@Slf4j
public class ChessController {
    public final UserService userService;
    public final ChessGameService gameService;

    public ChessController(UserService userService, ChessGameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @PostMapping("/{id}")
    public GameResponse movePiece(@PathVariable long id, @RequestBody MoveRequest request) {
        log.info("moving piece - {}", request);
        if (request.getStart() == request.getEnd()) {
            throw new RuntimeException("Bad request");
        }
        return gameService.movePiece(id, request);
    }

    @PostMapping("/{id}/draw")
    public GameResponse requestDraw(@PathVariable long id){
        log.info("requesting draw");
        return gameService.requestDraw(id);
    }
    @PostMapping("/{id}/forfeit")
    public GameResponse forfeit(@PathVariable long id){
        return gameService.requestDraw(id);
    }

    //    @GetMapping("/{id}")
//    public Map<Integer, PieceMoves> getAllMoves(@PathVariable long id){
//        log.info("fetching all legal moves");
//        return gameService.getAllMoves(id);
//    }

    @PostMapping
    public NewGameResponse startLocalGame(@RequestBody NewGameRequest request){
        log.info("start new game - {}", request);
        User user1 = userService.getUser(request.getUser1());
        User user2 = userService.getUser(request.getUser2());

        return gameService.startGame(user1, user2);
    }

    @PostMapping("/restart")
    public NewGameResponse restart(@RequestBody NewGameRequest request){
        log.info("the request is {}", request);
        User user1 = userService.getUser(request.getUser1());
        User user2 = userService.getUser(request.getUser2());

        return gameService.startGame(user1, user2);
    }

    @GetMapping("/rejoin/{gameId}")
    public NewGameResponse rejoinGame(@PathVariable long id){
        log.info("restarting game {}", id);
        return gameService.rejoinGame(id);
    }

    @PostMapping("/rejoin")
    public NewGameResponse rejoinAnyGame(@RequestBody NewGameRequest newGameRequest){
        String name = newGameRequest.getUser1();
        log.info("restarting game for [{}]", name);
        Long id = activeGames(name).stream().findFirst().orElseThrow(() -> new RuntimeException("The user is not in any active games"));
        return gameService.rejoinGame(id);
    }

    @GetMapping("/activeGames")
    public List<Long> activeGames(@RequestBody String name){
        log.info("getting active games - {}", name);
        return userService.getUsersGames(name);
    }
    @GetMapping("/activeGames2")
    public List<ChessGame> activeGames2(@RequestBody String name){
        log.info("getting active games - {}", name);
        return userService.getUsersGameInfo(name);
    }
}

