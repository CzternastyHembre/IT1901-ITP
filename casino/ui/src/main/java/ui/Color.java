package ui;

enum Color {
  RED("#a60000"),
  YELLOW("#cfc132"),
  BLUE("#1b32a8"),
  WHITE("ffffff"),
  GRAY("#4a4a4a"),
  BRIGHT_RED("ed3434"),
  LIME("12ff34");

  private String colorName;

  public String getColorName() {
    return colorName;
  }

  Color(String colorName){
    this.colorName = colorName;
  }

}
