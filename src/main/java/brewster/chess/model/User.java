package brewster.chess.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class User {
    private String name;
    private String email;
    private int wins;
    private int losses;
    private int draws;
    @OneToMany
    private List<Game> games;
}
