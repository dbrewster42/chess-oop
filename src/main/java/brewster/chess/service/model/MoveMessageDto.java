package brewster.chess.service.model;

import brewster.chess.model.Game;
import brewster.chess.model.piece.Piece;
import brewster.chess.model.request.MoveRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
@Builder
//@AllArgsConstructor
public class MoveMessageDto {
    private Game game;
    private String pieceName;
    private MoveRequest request;
    private Optional<Piece> potentialFoe;

}
