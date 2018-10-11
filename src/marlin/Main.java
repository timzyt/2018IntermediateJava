package marlin;

import marlin.GraphicsLib.Window;
import marlin.sandbox.PaintInk;
import marlin.sandbox.Squares;

public class Main {

  public static void main(String[] args) {
    //Window.PANEL = new Squares();
    Window.PANEL = new PaintInk();
    Window.launch();
  }
}
