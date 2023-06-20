package brewster.chess.repository;

import brewster.chess.model.ChessGame;
import brewster.chess.model.Player;
import brewster.chess.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
//    @Query("SELECT u.players FROM User u JOIN FETCH u.players WHERE u.name = :name")
    @Query("SELECT u.players FROM User u WHERE u.name = :name")
    List<Player> findUserPlayers(@Param("name") String name); //todo
//    @Query("SELECT * FROM ChessGame g WHERE player.game IN (SELECT u.players FROM User u WHERE u.name = :name) p")
    //    @Query("SELECT * FROM ChessGame g WHERE game IN (SELECT u.players FROM User u WHERE u.name = :name)")
//    @Query("SELECT p.game FROM (SELECT u.players FROM User u WHERE u.name = :name) p")
//@Query("SELECT p.game FROM Player p WHERE IN (SELECT u.players FROM User u WHERE u.name = :name)")
@Query("SELECT g FROM ChessGame g WHERE g IN (SELECT p FROM Player p WHERE p IN (SELECT u.players FROM User u WHERE u.name = :name))")
//    @Query("SELECT p.game FROM Player p WHERE name IN (SELECT u.players FROM User u WHERE u.name = :name)")
    List<ChessGame> findUserGames(@Param("name") String name);
//    @Query("SELECT * FROM ChessGame g WHERE g IN (SELECT p.game FROM Player p WHERE p IN (SELECT u.players FROM User u WHERE u.name = :name) )")
//    List<ChessGame> findUserGames2(@Param("name") String name);
    @Query("SELECT p.game FROM Player p WHERE p IN (SELECT u.players FROM User u WHERE u.name = :name)")
    List<ChessGame> findUserGames2(@Param("name") String name);
//    @Query("SELECT u FROM User u JOIN FETCH u.players WHERE u.name = :name")
//    User findUserWithPlayers(@Param("name") String name);
//        @Query("SELECT * FROM User u WHERE u.email = :email")
//    List<User> findAllWithEmail (@Param("email") String email);
}
