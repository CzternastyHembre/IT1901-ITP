package validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlotsValidator {

    public static boolean isJackpot(List<String> symbols) {
        return getIntegers(symbols).stream().distinct().count() == 1;
    }


    public static boolean isPerfectStraight(List<String> symbols) {
        return (( splitString(symbols.get(0))-splitString(symbols.get(1))) == 1 && splitString(symbols.get(1)) - splitString(symbols.get(2)) == 1)
                || (splitString(symbols.get(0))-splitString(symbols.get(1))) == -1 && splitString(symbols.get(1)) - splitString(symbols.get(2)) == -1;
    }

    public static boolean isStraight(List<String> symbols) {
        var copyOfSymbols = new ArrayList<>(symbols);
        Collections.sort(copyOfSymbols);
        Collections.reverse(copyOfSymbols);
        return (( splitString(copyOfSymbols.get(0)) - splitString(copyOfSymbols.get(1))) == 1
                && splitString(copyOfSymbols.get(1)) - splitString(copyOfSymbols.get(2)) == 1);
    }

    public static boolean isDevil(List<String> symbols) {
        return splitString(symbols.get(0)) == 6
                && splitString(symbols.get(1)) == 6
                && splitString(symbols.get(2)) == 6;
    }

    public static boolean isPair(List<String> symbols) {
        return getIntegers(symbols).stream().distinct().count() ==2;
    }

    private static int splitString(String string){
        return Character.getNumericValue(string.charAt(0));
    }

    private static ArrayList<Integer> getIntegers(List<String> symbols) {
        ArrayList<Integer> numberList = new ArrayList<>();
        for (String symbol : symbols) {
            numberList.add(splitString(symbol));
        }
        return numberList;
    }

}


