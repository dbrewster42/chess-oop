package brewster.chess.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void user(){
        User user = new User("james", "james@hotmail.com");
        assertThat(user.getWins()).isEqualTo(0);
//        user.addPlayer(new WhitePlayer(user, null));
//        assertThat(user.getPlayers().size()).isEqualTo(1);
        user.addWin();
        assertThat(user.getWins()).isEqualTo(1);
    }
}