package brewster.chess.api;

import brewster.chess.model.User;
import brewster.chess.model.request.UserRequest;
import brewster.chess.model.response.UserResponse;
import brewster.chess.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(@Valid @RequestBody UserRequest request){
        log.info("new user - {}", request);
        User user = userService.createUser(request);
        return user.getName() + " has been saved in the db";
    }
    @GetMapping
    public String login(@Valid @RequestBody UserRequest request){
        log.info("login - {}", request);
        User user = userService.getUser(request.getName());
        return user.getName() + " has been retrieved from the db";
    }

    @GetMapping("/info/{name}")
    public UserResponse info(@PathVariable String name){
        log.info("getting info - {}", name);
        return new UserResponse(userService.getUser(name));
    }

    @GetMapping("/activeGames")
    public List<Long> activeGames(@RequestBody String name){
        log.info("getting active games - {}", name);
        return userService.getUsersGames(name);
    }
//    @GetMapping("/activeGames2")
//    public List<ChessGame> activeGames2(@RequestBody String name){
//        log.info("getting active games - {}", name);
//        return userService.getUsersGameInfo(name);
//    }

//    @GetMapping("/activePlayers/{name}")
//    public List<Player> activePlayers(@PathVariable String name){
//        log.info("getting active players - {}", name);
//        return userService.getUsersPlayers(name);
//    }
}
