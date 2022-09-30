package brewster.chess.api;

import brewster.chess.exception.PieceNotFound;
import brewster.chess.model.User;
import brewster.chess.model.request.NewGameRequest;
import brewster.chess.model.request.UserRequest;
import brewster.chess.model.response.NewGameResponse;
import brewster.chess.repository.GameRepository;
import brewster.chess.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerTest {
    @Autowired Controller sut;
    long id = 1;
    @Autowired GameRepository gameRepository;
    @Autowired UserRepository userRepository;

    String name1;
    String name2;


//    @BeforeEach
//    void setup(){
//        Game game = new Game(createUser(), createUser2());
//        game.setId(id);
//        gameRepository.save(game);
//
//    }
    @Test
    @Order(1)
    void createUserTest(){
        name1 = "rainmaker";

        String response = sut.createUser(getUserRequest(name1));

        assertThat(response).isEqualTo(name1 + " has been saved in the db") ;
        assertThat(userRepository.findById(name1).isPresent()).isTrue();
    }

    @Test
    @Order(2)
    void startNewLocalGame(){
//        userRepository.save(new User("rainmaker", "rainmaker@gmail.com"));
        userRepository.save(new User("Bobby", "Bobby@gmail.com"));

        NewGameResponse response = sut.startNewLocalGame(getNewGameRequest());

        assertThat(response.getWhitePlayerName()).isEqualTo("rainmaker");
        assertThat(response.getWhitePlayers().size()).isEqualTo(16);
        assertThat(response.getBlackPlayers().size()).isEqualTo(16);
        assertThat(gameRepository.findById(response.getId()).isPresent()).isTrue();
    }

    @Test
    @Order(3)
    void selectPieceWrongTeam(){
        assertThrows(PieceNotFound.class, () -> sut.selectPiece(id, 88));
    }
    @Test
    @Order(3)
    void selectPieceEmpty(){
        assertThat(sut.selectPiece(id, 11)).isEmpty();
    }
    @Test
    @Order(3)
    void selectPiece(){
        assertThat(sut.selectPiece(id, 12).size()).isEqualTo(2);
        assertThat(sut.selectPiece(id, 21).size()).isEqualTo(2);
        assertThat(sut.selectPiece(id, 41).size()).isEqualTo(0);
    }

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