package brewster.chess.model.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<Type> promotionChoices() {
        return Arrays.stream(Type.values()).filter(type -> PAWN != type && type != KING).collect(Collectors.toList());
    }
}
