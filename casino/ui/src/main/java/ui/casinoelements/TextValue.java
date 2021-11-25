package ui.casinoelements;

/**
 * This enum provides the text value for the chip.
 */
public enum TextValue {
  ONE("  1"),
  FIVE("  5"),
  TWENTY_FIVE(" 25"),
  FIFTY(" 50"),
  HUNDRED("100"),
  FIVE_HUNDRED("500"),
  THOUSAND(" 1k"),
  FIVE_THOUSAND(" 5k"),
  TEN_THOUSAND("10k"),
  FIFTY_THOUSAND("50k");

  private String textValue;

  public String getTextValue() {
    return textValue;
  }

  TextValue(String textColor) {
    this.textValue = textColor;
  }
}
