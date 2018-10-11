
package marlin.sandbox;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Timer;
import marlin.GraphicsLib.G;
import marlin.GraphicsLib.Window;

public class Squares extends Window implements ActionListener {

  public static G.VS theVS = new G.VS(100, 100, 200, 300);
  public static Color theColor = G.rndColor();

  public static Square.List theList = new Square.List();
  public static Square backgroundSquare = new Square(0 ,0){

    @Override
    public void dn(int x, int y){
      theList.add(new Square(x, y));
    }
    public void drag(int x, int y){
      Square s = theList.get(theList.size() - 1);
      int w = Math.abs(x - s.loc.x);
      int h = Math.abs(y - s.loc.y);
      s.resize(w, h);
    }
    public void up(int x, int y){
      firstPressed.set(x, y);
    }
  };
  static {
    theList.add(backgroundSquare);
    backgroundSquare.size.set(3000, 3000);
    backgroundSquare.c = Color.white;
  }
  public static Square theSquare = new Square(200, 328);
  public static boolean dragging = false;
  public static G.V mousePosition = new G.V(0,0);
  static G.V firstPressed = new G.V(0,0);
  public static I.Area currentArea;
  public static Timer timer;



  public Squares() {
    super("squares", UC.screenHeight, UC.screenWidth);
    timer = new Timer(33, this);
    timer.setInitialDelay(5000);
    timer.start();
  }

  public void paintComponent(Graphics g) {
    G.fillBackground(g, Color.white);
    g.setColor(Color.blue);
    //g.fillRect(100, 100,200, 300);
    //theVS.fill(g, theColor);
    //theSquare.draw(g);
    theList.draw(g);
  }
  public void mousePressed(MouseEvent me) {
    //if(theVS.hit(me.getX(), me.getY())) {theColor = G.rndColor();}
    //theSquare = new Square(me.getX(), me.getY());
    int x = me.getX(), y = me.getY();
    firstPressed.set(x, y);
    theSquare = theList.hit(x, y);
    currentArea = theSquare;
    currentArea.dn(x, y);
    //    theSquare = theList.hit(x, y);
    //    if (theSquare == null) {
    //      theList.add(new Square(me.getX(), me.getY()));
    //      dragging = false;
    //    } else {
    //      theSquare.dv.set(0, 0);
    //      mousePosition.x = x - theSquare.loc.x;
    //      mousePosition.y = y - theSquare.loc.y;
    //    }
    repaint();
  }
  @Override
  public void mouseDragged (MouseEvent me) {
    int x = me.getX(), y = me.getY();
    currentArea.drag(x, y);
    //    if (dragging) {
    //      theSquare.loc.x = x;
    //      theSquare.loc.y = y;
    //    } else {
    //      Square s = theList.get(theList.size() - 1);
    //      int w = Math.abs(x - s.loc.x);
    //      int h = Math.abs(y - s.loc.y);
    //      s.resize(w, h);
    //    }
    repaint();
  }

  public void mouseReleased(MouseEvent me) {
    currentArea.up(me.getX(), me.getY());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
  }

  public static class Square extends G.VS implements I.Area{
    public Color c = G.rndColor();
    public G.V dv = new G.V(G.rnd(20) - 10, G.rnd(20) - 10);
    public Square (int x, int y) {super(x, y, 100, 100);}
    public void moveAndBounce() {
      loc.add(dv);
      if(lox() < 0 && dv.x < 0) {dv.x = -dv.x;}
      if(loy() < 0 && dv.y < 0) {dv.y = -dv.y;}
      if(hix() > 1000 && dv.x > 0) {dv.x = -dv.x;}
      if(hiy() > 800 && dv.y > 0) {dv.y = -dv.y;}
    }
    public void draw(Graphics g) {this.fill(g, c); moveAndBounce();}
    public void resize(int x, int y) {size.x = x; size.y = y;}
    public void dn(int x, int y){
      theSquare.dv.set(0, 0);
      mousePosition.x = x - theSquare.loc.x;
      mousePosition.y = y - theSquare.loc.y;}
    public void drag(int x, int y){
      mousePosition.x = x - theSquare.loc.x;
      mousePosition.y = y - theSquare.loc.y;}
    public void up(int x, int y){
      theSquare.dv.set(x - firstPressed.x, y - firstPressed.y);
    }

    public static class List extends ArrayList<Square> {
      public void draw(Graphics g){for (Square s : this) {s.draw(g);}}
      public Square hit(int x, int y) {
        Square result = null;
        for (Square s : this) {if (s.hit(x, y)) {result = s;}}
        return result;
      }
    }

  }
}
