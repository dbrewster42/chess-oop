package brewster.chess.service;


import brewster.chess.exception.InvalidMoveException;
import brewster.chess.exception.PieceNotFound;
import brewster.chess.model.Game;
import brewster.chess.model.request.MoveRequest;
import brewster.chess.piece.Piece;
import brewster.chess.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.List;

import static brewster.chess.mother.UserMother.createUser;
import static brewster.chess.mother.UserMother.createUser2;
import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;


class GameServiceTest {
    private GameRepository repository = mock(GameRepository.class);
//    private CheckService checkService = mock(CheckService.class);

    private GameService sut = new GameService(repository, new CheckService());
    private Game game = new Game(createUser(), createUser2());

//    @BeforeEach
//    void setup(){
//        trimPieces(game.getPlayer1().getPieces());
//        trimPieces(game.getPlayer2().getPieces());
//    }
    private void trimPieces(List<Piece> pieces){
        pieces.remove(1);
        pieces.remove(2);
        pieces.remove(3);
        pieces.remove(4);
        pieces.remove(5);
    }

    @Test
    void movePieceRemovesFoe(){
        sut.movePiece(game, getMoveRequest(52, 54));
        sut.movePiece(game, getMoveRequest(57, 55));

        sut.movePiece(game, getMoveRequest(61, 25));
        sut.movePiece(game, getMoveRequest(17, 16));

        sut.movePiece(game, getMoveRequest(25, 16));
        assertThat(sut.getLegalMoves(game, 27)).containsExactlyInAnyOrder(new Point(1, 6), new Point(2, 6), new Point(2, 5));
        sut.movePiece(game, getMoveRequest(27, 16));
        assertThat(game.getPlayer1().getPieces().size()).isEqualTo(15);
    }

    @Test
    void movePieceChecksCorrectTurn(){
        assertThrows(PieceNotFound.class, () -> sut.movePiece(game, getMoveRequest(17, 16)));
    }
    @Test
    void movePieceSetsCheckAndRequiresItToBeDefeated(){
        sut.movePiece(game, getMoveRequest(41, 47));
        assertThat(game.isCheck()).isTrue();
        assertThat(game.isWhitesTurn()).isFalse();

        assertThrows(InvalidMoveException.class, () -> sut.movePiece(game, getMoveRequest(17, 16)));
    }

    @Test
    void movePieceChecksCannotMoveIntoCheck(){
        sut.movePiece(game, getMoveRequest(41, 56));

        assertThrows(InvalidMoveException.class, () -> sut.movePiece(game, getMoveRequest(57, 66)));
    }
    @Test
    void movePieceChecksCannotMoveIntoCheck2() {
        assertThrows(InvalidMoveException.class, () -> sut.movePiece(game, getMoveRequest(51, 66)));
    }

    private MoveRequest getMoveRequest(int start, int end){
        MoveRequest moveRequest = new MoveRequest();
        moveRequest.setStart(start);
        moveRequest.setEnd(end);
        return moveRequest;
    }

}