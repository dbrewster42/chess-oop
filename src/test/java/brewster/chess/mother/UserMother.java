package brewster.chess.mother;

import brewster.chess.model.Player;
import brewster.chess.model.User;

import java.util.List;

public class UserMother {
    public static User createUser(){
        return new User("rainmaker", "rainmaker@gmail.com");

    }
    public static User createUser2(){
        return new User("Bobby", "bob@gmail.com");
    }


    public static Player createPlayer(User user){
        return new Player(user, true);
    }
    public static Player createPlayer2(User user){
        return new Player(user, false);
    }
}
