package marlin.sandbox;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import marlin.GraphicsLib.Window;

public class Paint extends Window {

  public static Random rnd = new Random();
  public static Path thePath = new Path();
  public static int clicks = 0;

  public static int rnd(int max) {
    return rnd.nextInt(max);
  }

  public Paint() {
    super("Paint", 1000, 800);
  }

  protected void paintComponent(Graphics g) {
    Color c = new Color(rnd(255), rnd(255), rnd(255));
    g.setColor(c);
    //g.drawOval(100, 100, 200, 300);
    g.fillOval(100, 100, 200, 300);
    String message = "clicks: " + clicks;
    int x = 400;
    int y = 200;
    g.drawOval(x, y, 3, 3);
    FontMetrics fm = g.getFontMetrics();
    int a = fm.getAscent();
    int d = fm.getDescent();
    int w = fm.stringWidth(message);
    g.setColor(new Color(rnd(255), rnd(255), rnd(255)));
    g.fillRect(x, y - a, w, a + d);
    g.setColor(new Color(rnd(255), rnd(255), rnd(255)));
    g.drawString(message, x, y);
    g.drawLine(100, 600, 600, 100);
    thePath.draw(g);
  }

  @Override
  public void mousePressed(MouseEvent me) {
    System.out.println("x= " + me.getX() + "y = " + me.getY());
    clicks++;
    thePath.clear();
    thePath.add(new Point(me.getPoint()));
    repaint();
  }

  @Override
  public void mouseDragged(MouseEvent me) {
    thePath.add(new Point(me.getPoint()));
    repaint();
  }

  public static class Path extends ArrayList<Point> {
    public void draw(Graphics g) {
      for (int i = 1; i < this.size(); i++) {
        Point p = this.get(i - 1);
        Point n = this.get(i);
        g.drawLine(p.x, p.y, n.x, n.y);
      }
    }
  }
}
