package brewster.chess.model.constant;

public enum Type {

    ROOK("Rook"),
    KNIGHT("Knight"),
    BISHOP("Bishop"),
    KING("King"),
    QUEEN("Queen"),
    PAWN("Pawn");

    public final String name;
    Type(String name){
        this.name = name;
    }
}
