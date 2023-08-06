package brewster.chess.api;

import brewster.chess.model.User;
import brewster.chess.model.request.UserRequest;
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
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(@RequestBody UserRequest request){
        log.info("new user - {}", request);
        User user = userService.createUser(request);
//        if (isValid)
        return user.getName() + " has been saved in the db";
    }
    @GetMapping
    public String login(@RequestBody UserRequest request){
        log.info("login - {}", request);
        User user = userService.getUser(request.getName());
//        if (isValid)
        return user.getName() + " has been retrieved from the db";
    }

    @GetMapping("/info/{name}")
    public User info(@PathVariable String name){
        log.info("getting info - {}", name);
        return userService.getUser(name);
    }

//    @GetMapping("/activePlayers/{name}")
//    public List<Player> activePlayers(@PathVariable String name){
//        log.info("getting active players - {}", name);
//        return userService.getUsersPlayers(name);
//    }
}
