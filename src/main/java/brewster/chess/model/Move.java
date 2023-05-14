package brewster.chess.model;

import brewster.chess.model.constant.SpecialMove;
import brewster.chess.model.piece.Piece;
import lombok.Data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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

//    @Column(n)
    private SpecialMove specialMove;
    private String potentialFoe;
//    @OneToOne
//    private Piece potentialFoe;
}
