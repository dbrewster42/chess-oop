package brewster.chess.mother;

import brewster.chess.model.request.NewGameRequest;
import brewster.chess.model.request.UserRequest;

public class RequestMother {
    public static NewGameRequest getNewGameRequest(String name1, String name2){
        NewGameRequest request = new NewGameRequest();
        request.setUser1(name1);
        request.setUser2(name2);
        return request;
    }

    public static UserRequest getUserRequest(String name){
        UserRequest userRequest = new UserRequest();
        userRequest.setName(name);
        userRequest.setEmail(name + "@gmail.com");
        return userRequest;
    }
}
