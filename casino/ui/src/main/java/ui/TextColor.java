package ui;


public enum TextColor {
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

  private String textColor;

  public String getTextColor() {
    return textColor;
  }

  TextColor(String textColor){
    this.textColor = textColor;
  }
}
