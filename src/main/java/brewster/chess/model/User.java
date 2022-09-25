package brewster.chess.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Table(name = "UserEntity")
@Getter
@Entity
public class User {
    @Id
    private final String name;
    private final String email;
    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private final List<Player> players;

    private int wins;
    private int losses;
    private int draws;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        players = new ArrayList<>();
    }

    public User() {
        this.name = null;
        this.email = null;
        players = new ArrayList<>();
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void addWin(){ wins++; }
    public void addLoss(){ losses++; }
    public void addDraw(){ draws++; }

}
