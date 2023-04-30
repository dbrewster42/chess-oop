package brewster.chess.repository;

import brewster.chess.model.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Long> {

    @Query("SELECT g FROM Game g LEFT JOIN FETCH g.moves WHERE g.id = :id")
    Optional<Game> findGameWithMoves(@Param("id") Long id);
}
