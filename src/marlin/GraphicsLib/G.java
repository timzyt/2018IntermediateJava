package marlin.GraphicsLib;

import java.awt.Color;
import java.util.Random;
import java.awt.*;

public class G {
  public static Random RND = new Random();

  public static int rnd(int max) {return RND.nextInt(max);}
  public static Color rndColor() {return new Color(rnd(256), rnd(256), rnd(256));}
  public static void fillBackground(Graphics g, Color c) {g.setColor(c); g.fillRect(0,0,3000,3000);}
  public static void drawCircle(Graphics g, int x, int y, int r) {g.drawOval(x - r, y - r, r + r, r + r);}
  public static class V{
    public static Transform T;
    public int x, y;
    public V(V v) {this.x = v.x; this.y = v.y;}
    public V(int x, int y) {this.x = x; this.y = y;}
    public void add(V v) {this.x += v.x; this.y += v.y;}
    public void set(V v){this.x = v.x; this.y = v.y;}
    public void set(int x, int y){this.x = x; this.y = y;}
    public int tx() {return this.x * T.newScale / T.oldScale + T.dx;}
    public int ty() {return this.y * T.newScale / T.oldScale + T.dy;}
    public static class Transform {
      int dx, dy, oldScale, newScale;
      //isomorphic scale: pick the higher scale from either height and width, and use that to scale the box
      public void setScale(int oldW, int oldH, int newW, int newH) {
        this.oldScale = oldW < oldH ? oldH : oldW;
        this.newScale = newW < newH ? newH : newW;
      }
      public int trans(int oldX, int oldW, int newX, int newW) {
        return (-oldX - oldW / 2) * newW / oldW + newX + newW / 2;
      }
      public void set(VS from, VS to) {
        setScale(from.size.x, from.size.y, to.size.x, to.size.y);
        dx = trans(from.loc.x, from.size.x, to.loc.x, to.size.x);
        dy = trans(from.loc.y, from.size.y, to.loc.y, to.size.y);
      }
      public void set(BBox from, VS to) {
        setScale(from.h.size(), from.p.size(), to.size.x, to.size.y);
        dx = trans(from.h.lo, from.h.size(), to.loc.x, to.size.x);
        dy = trans(from.p.lo, from.p.size(), to.loc.y, to.size.y);
      }
    }
  }
  public static class VS{
    public V loc, size;
    public VS(int x, int y, int w, int h) {loc = new V(x, y); size = new V(w, h);}
    public void fill(Graphics g, Color c) {g.setColor(c); g.fillRect(loc.x, loc.y, size.x, size.y);}
    public boolean hit(int x, int y) {return loc.x <= x && loc.y <= y && x <= (loc.x + size.y) && y <= (loc.y + size.y);}
    public void resize(int x, int y) {size.x = x; size.y = y;}
    public int lox() {return loc.x;}
    public int midx() {return loc.x + size.x / 2;}
    public int hix() {return loc.x + size.x;}
    public int loy() {return loc.y;}
    public int midy() {return loc.y + size.y / 2;}
    public int hiy() {return loc.y + size.y;}
  }
  public static class LoHi{
    int lo, hi;
    public LoHi(int min, int max) {this.lo = min; this.hi = max;}
    public void set(int v) {this.lo = v; this.hi = v;}
    public void add(int v) {lo = (v < lo) ? v : lo; hi = (v > hi) ? v : hi;}
    public int size() {return lo < hi ? hi - lo : 1;}
  }
  public static class BBox{
    LoHi h, p;
    public BBox() {h = new LoHi(0, 0); p = new LoHi(0, 0);}
    public void set(int x, int y) {h.set(x); p.set(y);}
    public void add(int x, int y) {h.add(x); p.add(y);}
    public void add(V v) {h.add(v.x); p.add(v.y);}
    public VS getNewVS() {return new VS(h.lo, p.lo, h.size(), p.size());}
    public void draw(Graphics g) {g.drawRect(h.lo, p.lo, h.size(), p.size());}

  }

  /**
   * PolyLine class, list of points connected together by draw function
   */
  public static class PL{
    public V[] points;

    public PL(int count) {
      points = new V[count];
      for (int i = 0; i < count; i++) {points[i] = new V(0,0);}
    }

    public int size() {return points.length;}

    public void drawN(Graphics g, int n) {
      for (int i = 1; i < n; i++) {
        g.drawLine(points[i - 1].x, points[i - 1].y, points[i].x, points[i].y);
      }
      //drawNdots(g, n);
    }

    public void drawNdots(Graphics g, int n) {
      for (int i  = 0; i < n; i++) {
        drawCircle(g, points[i].x, points[i].y, 4);
      }
    }

    public void draw(Graphics g) {drawN(g, size());}

    public void transform() {
      for (int i = 0; i < this.points.length; i++) {
        points[i].set(points[i].tx(), points[i].ty());
      }
    }
  }
}
