package brewster.chess.api;

import brewster.chess.model.User;
import brewster.chess.model.request.MoveRequest;
import brewster.chess.model.response.GameResponse;
import brewster.chess.model.response.NewGameResponse;
import brewster.chess.model.response.StatusResponse;
import brewster.chess.repository.GameRepository;
import brewster.chess.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static brewster.chess.mother.RequestMother.getNewGameRequest;
import static brewster.chess.mother.RequestMother.getUserRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationTest {
    @Autowired
    UserController userController;
    @Autowired
    ChessController sut;
    long id = 1;
    @Autowired GameRepository gameRepository;
    @Autowired UserRepository userRepository;

    private final String whitePlayer = "rainmaker";
    private final String blackPlayer = "Bobby";


    @Test
    @Order(1)
    void createUserTest(){
        String response = userController.createUser(getUserRequest(whitePlayer));

        assertThat(response).isEqualTo(whitePlayer + " has been saved in the db") ;
        assertThat(userRepository.findById(whitePlayer).isPresent()).isTrue();
    }

    @Test
    @Order(2)
    void startNewLocalGame(){
        userRepository.save(new User(blackPlayer, "Bobby@gmail.com"));

        NewGameResponse response = sut.startLocalGame(getNewGameRequest(whitePlayer, blackPlayer));

        assertThat(response.getWhitePlayer()).isEqualTo(whitePlayer);
        assertThat(response.getBlackPlayer()).isEqualTo(blackPlayer);
        assertThat(response.getPieces().size()).isEqualTo(32);
        assertThat(gameRepository.findById(response.getId()).isPresent()).isTrue();
    }

    @Test
    @Order(3)
    void getLegalMoves(){
        assertThat(sut.getLegalMoves(id).values().size()).isEqualTo(10);
        List<Integer> allMoves = sut.getLegalMoves(id).values().stream()
            .reduce((a, b) -> {
                a.addAll(b);
                return a;
            }).orElseThrow();
        assertThat(allMoves.size()).isEqualTo(20);
    }

    @Test
    @Order(4)
    void movePieceFirst() {
        GameResponse response = sut.movePiece(id, getMoveRequest(52, 54));

        StatusResponse status = response.getStatus();
        assertThat(status.isActive()).isTrue();
        assertThat(status.isWhite()).isFalse();
        assertThat(status.isCheck()).isFalse();
        assertThat(response.getPieces().size()).isEqualTo(32);
    }

    @Test
    @Order(5)
    void movePiece() {
        sut.movePiece(id, getMoveRequest(47, 45));

        GameResponse response = sut.movePiece(id, getMoveRequest(61, 16));

        assertThat(response.getStatus().isWhite()).isFalse();
        assertThat(response.getPieces().size()).isEqualTo(32);

        response = sut.movePiece(id, getMoveRequest(77, 16));

        assertThat(response.getStatus().isWhite()).isTrue();
        assertThat(response.getPieces().size()).isEqualTo(31);
    }

    @Test
    @Order(6)
    void movePieceWithMessage() {
        GameResponse response = sut.movePiece(id, getMoveRequest(41, 63));

        StatusResponse status = response.getStatus();
        assertThat(status.isActive()).isTrue();
        assertThat(status.isWhite()).isFalse();
        assertThat(status.isCheck()).isFalse();
        assertThat(response.getPieces().size()).isEqualTo(31);
        assertThat(response.getMoves())
            .containsExactly("rainmaker has moved his Pawn from E2 to E4",
                "Bobby has moved his Pawn from D7 to D5",
                "rainmaker has moved his Bishop from F1 to A6",
                "Bobby has moved his Pawn from G7 to A6 and has captured a BISHOP",
                "rainmaker has moved his Queen from D1 to F3");
//        assertThat(response.getMoves()).isEqualTo("1. rainmaker has moved his Pawn from E2 to E4\n" +
//                "2. Bobby has moved his Pawn from D7 to D5\n" +
//                "3. rainmaker has moved his Bishop from F1 to A6\n" +
//                "4. Bobby has moved his Pawn from G7 to A6 and has captured a BISHOP\n" +
//                "5. rainmaker has moved his Queen from D1 to F3\n");
    }

    private MoveRequest getMoveRequest(int start, int end){
        MoveRequest request = new MoveRequest();
        request.setStart(start);
        request.setEnd(end);
        return request;
    }


}