package brewster.chess.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class User {
    private final String name;
    private final String email;
    @OneToMany
    private List<Player> players;
    private int wins;
    private int losses;
    private int draws;
}
