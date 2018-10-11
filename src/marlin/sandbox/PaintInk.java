package marlin.sandbox;

import java.awt.*;
import java.awt.Graphics;

import java.awt.event.MouseEvent;
import marlin.GraphicsLib.G;
import marlin.GraphicsLib.Window;

public class PaintInk extends Window {
  public static Ink.List inkList = new Ink.List();

  public PaintInk() {super("PaintInk", UC.screenWidth, UC.screenHeight);}


  public void paintComponent(Graphics g) {
    G.fillBackground(g, Color.white);

    //this line below draws a square.
    //g.setColor(Color.red); g.fillRect(100,100,100,100);
    g.drawString("points :" + Ink.BUFFER.n, 600, 30);
    inkList.show(g);

    Ink.BUFFER.show(g);
  }

  public void mousePressed(MouseEvent me) {
    Ink.BUFFER.dn(me.getX(), me.getY());
    repaint();
  }

  public void mouseDragged(MouseEvent me) {
    Ink.BUFFER.drag(me.getX(), me.getY());
    repaint();
  }

  public void mouseReleased(MouseEvent me) {
    inkList.add(new Ink());
    repaint();
  }

}
