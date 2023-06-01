package brewster.chess.model;

import brewster.chess.model.constant.SpecialMove;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
//    private ChessGame game;

    private String pieceName;
    private int start;
    private int end;

    private SpecialMove specialMove;
    private String potentialFoe;
}
