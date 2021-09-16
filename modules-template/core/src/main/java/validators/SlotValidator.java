package validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlotValidator {

    public static boolean isJackpot(List<Integer> symbols) {
        return symbols.stream().distinct().count() == 1;
    }

    public static boolean isPerfectStraight(List<Integer> symbols) {
        return ((symbols.get(0)-symbols.get(1)) == 1 && symbols.get(1) - symbols.get(2) == 1)
                || (symbols.get(0)-symbols.get(1)) == -1 && symbols.get(1) - symbols.get(2) == -1;
    }

    public static boolean isStraight(List<Integer> symbols) {
        var copyOfSymbols = new ArrayList<>(symbols);
        Collections.sort(copyOfSymbols);
        Collections.reverse(copyOfSymbols);
        return ((copyOfSymbols.get(0) - copyOfSymbols.get(1)) == 1 && copyOfSymbols.get(1) - copyOfSymbols.get(2) == 1);
    }

    public static boolean isDevil(List<Integer> symbols) {
        return symbols.get(0) == 6 && symbols.get(1) ==6 && symbols.get(2) == 6;
    }

    public static boolean isPair(List<Integer> symbols) {
        return symbols.stream().distinct().count() ==2;
    }
}
