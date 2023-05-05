package brewster.chess.service.model;

import brewster.chess.model.ChessGame;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.request.MoveRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
//@AllArgsConstructor
public class MoveMessageDto {
    private ChessGame game;
    private String pieceName;
    private MoveRequest request;
    private Optional<Piece> potentialFoe;

}
