package validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class which checks the different combinations that the slot can give out.
 */
public class SlotsValidator {

  public static boolean isSuperJackpot(List<String> symbols) { // Jackpot and Flush
    return getIntegers(symbols).stream().distinct().count() == 1 && isFlush(symbols);
  }

  public static boolean isJackpot(List<String> symbols) { // F.eks 333
    return getIntegers(symbols).stream().distinct().count() == 1;
  }

  public static boolean isSuperPerfectStraight(List<String> symbols) { // Perfect Straight and Flush
    return (isPerfectStraight(symbols) && isFlush(symbols));
  }

 
  /**
 * Method for 
 */

  public static boolean isPerfectStraight(List<String> symbols) { 
    int first = splitStringForInt(symbols.get(0));
    int second = splitStringForInt(symbols.get(1));
    int third = splitStringForInt(symbols.get(2));
    return isOneMore(first, second) && isOneMore(second, third) || isOneLess(first, second) && isOneLess(second, third);
  }


  public static boolean isStraight(List<String> symbols) { // F.eks 354, 798, 132
    var copyOfSymbols = new ArrayList<>(symbols);

    Collections.sort(copyOfSymbols); // 354 becomes 345
    Collections.reverse(copyOfSymbols); // 543

    return ((splitStringForInt(copyOfSymbols.get(0)) - splitStringForInt(copyOfSymbols.get(1))) == 1 // 5-4 = 1
        && splitStringForInt(copyOfSymbols.get(1)) - splitStringForInt(copyOfSymbols.get(2)) == 1); // 4-3 = 1, then
                                                                                                    // straight
  }

  public static boolean isDevil(List<String> symbols) { // 666
    return splitStringForInt(symbols.get(0)) == 6 && splitStringForInt(symbols.get(1)) == 6
        && splitStringForInt(symbols.get(2)) == 6;
  }

  public static boolean isFlush(List<String> symbols) { // Same suit
    return getSuits(symbols).stream().distinct().count() == 1;
  }


  public static boolean isPair(List<String> symbols) {
    return getIntegers(symbols).stream().distinct().count() == 2;
  }


  // Help methods
  private static int splitStringForInt(String string) {
    return Character.getNumericValue(string.charAt(0));
  }

  private static char splitStringForSuit(String string) {
    return string.charAt(1);
  }

  private static ArrayList<Character> getSuits(List<String> symbols) {
    ArrayList<Character> suitList = new ArrayList<>();
    for (String symbol : symbols) {
      suitList.add(splitStringForSuit(symbol));
    }
    return suitList;
  }

  private static ArrayList<Integer> getIntegers(List<String> symbols) {
    ArrayList<Integer> numberList = new ArrayList<>();
    for (String symbol : symbols) {
      numberList.add(splitStringForInt(symbol));
    }
    return numberList;
  }


  private static boolean isOneMore(int first, int second) {
    return first - second == 1;
  }

  private static boolean isOneLess(int first, int second) {
    return first - second == -1;
  }
}


