package validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

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
   *Checks if the result is a perfect straight.
   *
   * @param symbols is list contains of the symbols in the slots.
   * @return true if cards form a perfect straight. I.e. 345 or 543.
   */
  public static boolean isPerfectStraight(List<String> symbols) { 
    int first = splitStringForInt(symbols.get(0));
    int second = splitStringForInt(symbols.get(1));
    int third = splitStringForInt(symbols.get(2));
    return isIncrementingOrDecrementing(first, second, third);
  }

  /**
   * Checks if the result of the slot spin is a straight.
   *
   * @param symbols is list contains of the symbols in the slots.
   * @return true if straight. Eg. 354 or 435. As long as
   */

  public static boolean isStraight(List<String> symbols) { // F.eks 354, 798, 132
    var copyOfSymbols = new ArrayList<>(symbols);

    Collections.sort(copyOfSymbols); // 354 becomes sorted

    var first = splitStringForInt(copyOfSymbols.get(0));
    var second = splitStringForInt(copyOfSymbols.get(1));
    var third = splitStringForInt(copyOfSymbols.get(2));

    return isIncrementing(first, second, third);
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
    String substring = string.substring(0, string.indexOf(string.charAt(string.length() - 1)));
    return Integer.parseInt(substring);
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

  private static boolean isDecrementing(int ...ints) {
    for (int i = 0; i < ints.length - 1; i++) {
      if (ints[i] - ints[i + 1] != 1) {
        return false;
      }
    }
    return true;
  }

  private static boolean isIncrementing(int ...ints) {
    //Flipping the array
    return isDecrementing(IntStream.range(0, ints.length).map(i -> ints[ints.length - i - 1])
            .toArray());
  }

  private static boolean isIncrementingOrDecrementing(int ...ints) {
    return isIncrementing(ints) || isDecrementing(ints);
  }
}


