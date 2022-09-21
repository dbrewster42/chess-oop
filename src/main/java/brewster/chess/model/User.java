package brewster.chess.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Table(name = "UserEntity")
@Data
@Entity
public class User {
    @Id
    private final String name;
    private final String email;
    @OneToMany(mappedBy="user")
    private List<Player> players = new ArrayList<>();

    private int wins;
    private int losses;
    private int draws;

    public void addPlayer(Player player){
        players.add(player);
    }
}
