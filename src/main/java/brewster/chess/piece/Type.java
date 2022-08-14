package brewster.chess.piece;

import lombok.Getter;

@Getter
public enum Type {

    ROOK("Rook"),
    KNIGHT("Knight"),
    BISHOP("Bishop"),
    KING("King"),
    QUEEN("Queen"),
    PAWN("Pawn");

    private String name;
    Type(String name){
        this.name = name;
    }
}
