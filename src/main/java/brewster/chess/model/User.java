package brewster.chess.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    private final String name;

    private final String email;
    @OneToMany //(mappedBy="User")
    private List<Player> players = new ArrayList<>();

    private int wins;
    private int losses;
    private int draws;

    public void addPlayer(Player player){
        players.add(player);
    }
}
