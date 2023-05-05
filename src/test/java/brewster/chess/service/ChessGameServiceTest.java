package brewster.chess.service;


import brewster.chess.exception.InvalidMoveException;
import brewster.chess.exception.PieceNotFound;
import brewster.chess.model.ChessGame;
import brewster.chess.model.request.MoveRequest;
import brewster.chess.model.response.GameResponse;
import brewster.chess.model.piece.Piece;
import brewster.chess.repository.GameRepository;
import brewster.chess.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static brewster.chess.mother.UserMother.createUser;
import static brewster.chess.mother.UserMother.createUser2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ChessGameServiceTest {
    private final GameRepository repository = mock(GameRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);


    private final ChessGameService sut = new ChessGameService(repository, new CheckService(), userRepository, new MoveMessageService());
    private ChessGame game;

    @BeforeEach
    void setup(){
        game = new ChessGame(createUser(), createUser2());
    }

    @Test
    void movePieceRemovesFoe(){
        when(repository.findById(game.getId())).thenReturn(Optional.of(game));

        sut.movePiece(game, getMoveRequest(52, 54));
        sut.movePiece(game, getMoveRequest(57, 55));

        sut.movePiece(game, getMoveRequest(61, 25));
        sut.movePiece(game, getMoveRequest(17, 16));

        sut.movePiece(game, getMoveRequest(25, 16));
        assertThat(sut.getLegalMoves(game.getId(), 27)).containsExactlyInAnyOrder(16, 26, 25);
        sut.movePiece(game, getMoveRequest(27, 16));
        assertThat(game.getWhitePlayer().getPieces().size()).isEqualTo(15);
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

    @Test
    void movePieceGetsCheckMate() {
        trimPieces(game.getWhitePlayer().getPieces());
        trimPieces(game.getBlackPlayer().getPieces());

        sut.movePiece(game, getMoveRequest(31, 84));
        sut.movePiece(game, getMoveRequest(27, 26));

        GameResponse response = sut.movePiece(game, getMoveRequest(41, 48));
        assertThat(response.getStatus().isActive()).isFalse();
        assertThat(response.getMoves()).contains("rainmaker has checkmated Bobby! rainmaker wins!");
//        assertThat(response.getMoves()).contains(game.getWhitePlayer().getName() + " has checkmated " + game.getBlackPlayer().getName());
    }

    @Test
    void CheckMateCanBeDefeatedByKingCapture() {
        GameResponse response = sut.movePiece(game, getMoveRequest(41, 48));

        assertThat(game.isCheck()).isTrue();
        assertThat(game.isWhitesTurn()).isFalse();
        assertThat(response.getStatus().isActive()).isTrue();
    }

    @Test
    void mustMoveOutOfCheck() {
        trimPieces(game.getWhitePlayer().getPieces());
        trimPieces(game.getBlackPlayer().getPieces());
        sut.movePiece(game, getMoveRequest(31, 84));
        sut.movePiece(game, getMoveRequest(48, 42));

        assertThrows(InvalidMoveException.class, () -> sut.movePiece(game, getMoveRequest(41, 48)));
    }

    @Test
    void CheckMateCanBeDefeatedByBlock() {
        trimPieces(game.getWhitePlayer().getPieces());
        trimPieces(game.getBlackPlayer().getPieces());
        GameResponse response = sut.movePiece(game, getMoveRequest(41, 55));

        assertThat(response.getStatus().isActive()).isTrue();
        assertThat(game.isCheck()).isTrue();
        assertThat(game.isWhitesTurn()).isFalse();
    }

    @Test
    
    void CheckMateCanBeDefeatedByCapture() {
        trimPieces(game.getWhitePlayer().getPieces());
        trimPieces(game.getBlackPlayer().getPieces());

        sut.movePiece(game, getMoveRequest(31, 84));
        sut.movePiece(game, getMoveRequest(48, 14));;

        GameResponse response = sut.movePiece(game, getMoveRequest(41, 57));

        assertThat(response.getStatus().isActive()).isTrue();
        assertThat(game.isCheck()).isTrue();
        assertThat(game.isWhitesTurn()).isFalse();
    }
    @Test
    void CheckMateCanBeDefeatedByCapture2() {
        trimPieces(game.getWhitePlayer().getPieces());
        trimPieces(game.getBlackPlayer().getPieces());

        sut.movePiece(game, getMoveRequest(31, 84));
        sut.movePiece(game, getMoveRequest(27, 26));

        GameResponse response = sut.movePiece(game, getMoveRequest(41, 57));

        assertThat(response.getStatus().isActive()).isTrue();
        assertThat(game.isCheck()).isTrue();
        assertThat(game.isWhitesTurn()).isFalse();
    }

    private MoveRequest getMoveRequest(int start, int end){
        MoveRequest moveRequest = new MoveRequest();
        moveRequest.setStart(start);
        moveRequest.setEnd(end);
        return moveRequest;
    }

    private void trimPieces(List<Piece> pieces){
        pieces.remove(8);
        pieces.remove(9);
        pieces.remove(10);
        pieces.remove(11);
    }
}