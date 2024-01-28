package brewster.chess.model.response;

import brewster.chess.model.User;
import lombok.Getter;

@Getter
public class UserResponse {
    private String name;
    private int activeGames;
    private int wins;
    private int losses;
    private int draws;

    public UserResponse(User user) {
        this.name = user.getName();
        this.activeGames = user.getPlayers().size();
        this.wins = user.getWins();
        this.losses = user.getLosses();
        this.draws = user.getDraws();
    }
}
