package brewster.chess.api;

import brewster.chess.model.Game;
import brewster.chess.model.Player;
import brewster.chess.model.User;
import brewster.chess.model.request.NewGameRequest;
import brewster.chess.model.request.UserRequest;
import brewster.chess.model.response.NewGameResponse;
import brewster.chess.repository.GameRepository;
import brewster.chess.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ControllerTest {
    @Autowired Controller sut;
    int id = 1;
    @Autowired GameRepository gameRepository;
    @Autowired UserRepository userRepository;


//    @BeforeEach
//    void setup(){
//        Game game = new Game(createUser(), createUser2());
//        game.setId(id);
//        gameRepository.save(game);
//
//    }
    @Test
    void createUserTest(){
        String name = "rainmaker";

        String response = sut.createUser(getUserRequest(name));

        assertThat(response).isEqualTo("rainmaker has been saved in the db") ;
        assertThat(userRepository.findById(name).isPresent()).isTrue();
    }

    @Test
    void startNewLocalGame(){
        NewGameResponse response = sut.startNewLocalGame(getNewGameRequest());

        assertThat(response.getWhitePlayerName()).isEqualTo("rainmaker");
        assertThat(response.getWhitePlayers().size()).isEqualTo(16);
        assertThat(response.getBlackPlayers().size()).isEqualTo(16);
        assertThat(gameRepository.findById(response.getId()).isPresent()).isTrue();
    }

//    @Test
//    void createPlayerTest() {
//    }
//
//    @Test
//    void selectPiece() {
//    }
//
//    @Test
//    void selectPromotion() {
//    }

    private NewGameRequest getNewGameRequest(){
        NewGameRequest request = new NewGameRequest();
        request.setUser1("rainmaker");
        request.setUser2("Bobby");
        return request;
    }

    private UserRequest getUserRequest(String name){
        UserRequest userRequest = new UserRequest();
        userRequest.setName(name);
        userRequest.setEmail("rainmaker@gmail.com");
        return userRequest;
    }


}