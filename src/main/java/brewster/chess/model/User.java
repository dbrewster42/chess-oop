package brewster.chess.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    private final String email;
    private final String name;
    @OneToMany
    private List<Player> players;
    private int wins;
    private int losses;
    private int draws;
}
