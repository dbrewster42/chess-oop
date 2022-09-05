package brewster.chess.model.request;

import lombok.Data;

@Data
public class NewGameRequest {
    private String user1;
    private String user2;
}
