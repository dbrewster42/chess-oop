package brewster.chess.api;

import brewster.chess.model.Game;
import brewster.chess.player.Player;
import brewster.chess.model.User;
import brewster.chess.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ControllerTest {
    @Autowired Controller sut;
    int id = 1;
    @Autowired GameRepository gameRepository;


    @BeforeEach
    void setup(){
        Game game = new Game(createUser(), createUser2());
        game.setId(id);
        gameRepository.save(game);

    }

    @Test
    void createPlayerTest() {
    }

    @Test
    void selectPiece() {
    }

    @Test
    void selectPromotion() {
    }

    private User createUser(){
        User user = new User("rainmaker@gmail.com", "rainmaker");
        user.setPlayers(List.of(createPlayer(user)));
        return user;
    }
    private User createUser2(){
        User user = new User("bob@gmail.com", "Bobby");
        user.setPlayers(List.of(createPlayer2(user)));
        return user;
    }//todo GameService should createPlayer on start


    private Player createPlayer(User user){
        return new Player(user, true);
    }
    private Player createPlayer2(User user){
        return new Player(user, false);
    }
}