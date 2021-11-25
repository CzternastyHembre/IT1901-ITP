package ui.casinoelements;

enum ChipColor {
  RED("#a60000"),
  YELLOW("#cfc132"),
  BLUE("#1b32a8"),
  WHITE("ffffff"),
  GRAY("#4a4a4a"),
  BRIGHT_RED("#ed3434"),
  LIME("#12ff34"),
  ORANGE("#ff7912"),
  PURPLE("#7912ff"),
  PINK("#ff12f3");

  private String colorName;

  public String getColorName() {
    return colorName;
  }

  ChipColor(String colorName) {
    this.colorName = colorName;
  }

}
