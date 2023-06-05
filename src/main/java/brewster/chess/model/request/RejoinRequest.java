package brewster.chess.model.request;

import lombok.Data;

@Data
public class RejoinRequest {
    private String user;
    private long gameId;
}
