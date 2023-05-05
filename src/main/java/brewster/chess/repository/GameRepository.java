package brewster.chess.repository;

import brewster.chess.model.ChessGame;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GameRepository extends CrudRepository<ChessGame, Long> {

    @Query("SELECT g FROM ChessGame g LEFT JOIN FETCH g.moves WHERE g.id = :id")
    Optional<ChessGame> findGameWithMoves(@Param("id") long id);
}
