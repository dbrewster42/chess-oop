package brewster.chess.api;

import brewster.chess.exception.PieceNotFound;
import brewster.chess.model.User;
import brewster.chess.model.request.MoveRequest;
import brewster.chess.model.request.NewGameRequest;
import brewster.chess.model.request.UserRequest;
import brewster.chess.model.response.GameResponse;
import brewster.chess.model.response.NewGameResponse;
import brewster.chess.repository.GameRepository;
import brewster.chess.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import brewster.chess.model.piece.Spot;

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
        assertThat(sut.selectPiece(id, 21).size()).isEqualTo(2);
        assertThat(sut.selectPiece(id, 41).size()).isEqualTo(0);
        assertThat(sut.selectPiece(id, 52).size()).isEqualTo(2);
    }
    @Test
    @Order(4)
    void movePieceFirst() {
        GameResponse response = sut.movePiece(id, getMoveRequest(52, 54));

        assertThat(response.isActive()).isTrue();
        assertThat(response.isWhite()).isFalse();
        assertThat(response.isCheck()).isFalse();
        assertThat(response.getWhitePlayers().size()).isEqualTo(16);
    }

    @Test
    @Order(5)
    void movePiece() {
        int spot = 47;
        Spot moveTo = sut.selectPiece(id, spot).get(1);

        sut.movePiece(id, getMoveRequest(spot,  moveTo.x * 10 + moveTo.y));
        assertThrows(PieceNotFound.class, () -> sut.selectPiece(id, 88));

        GameResponse response = sut.movePiece(id, getMoveRequest(61, 16));

        assertThat(response.isWhite()).isFalse();
        assertThat(response.getWhitePlayers().size()).isEqualTo(16);

        response = sut.movePiece(id, getMoveRequest(77, 16));

        assertThat(response.isWhite()).isTrue();
        assertThat(response.getWhitePlayers().size()).isEqualTo(15);
    }

    @Test
    @Order(6)
    void movePieceWithMessage() {
        GameResponse response = sut.movePiece(id, getMoveRequest(41, 63));

        assertThat(response.isActive()).isTrue();
        assertThat(response.isWhite()).isFalse();
        assertThat(response.isCheck()).isFalse();
        assertThat(response.getWhitePlayers().size()).isEqualTo(15);
//        assertThat(response.getMessage()).isEqualTo("1. rainmaker has moved his Pawn from 52 to 54\n" +
//                "2. Bobby has moved his Pawn from 47 to 45\n" +
//                "3. rainmaker has moved his Bishop from 61 to 16\n" +
//                "4. Bobby has moved his Pawn from 77 to 16 and has captured a BISHOP\n" +
//                "5. rainmaker has moved his Queen from 41 to 63\n");
        assertThat(response.getMessage()).isEqualTo("1. rainmaker has moved his Pawn from E2 to E4\n" +
                "2. Bobby has moved his Pawn from D7 to D5\n" +
                "3. rainmaker has moved his Bishop from F1 to A6\n" +
                "4. Bobby has moved his Pawn from G7 to A6 and has captured a BISHOP\n" +
                "5. rainmaker has moved his Queen from D1 to F3\n");
    }
//    @Test
//    void selectPromotion() {
//    }
    private MoveRequest getMoveRequest(int start, int end){
        MoveRequest request = new MoveRequest();
        request.setStart(start);
        request.setEnd(end);
        return request;
    }

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