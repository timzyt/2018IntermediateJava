package marlin.sandbox;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import marlin.GraphicsLib.G;

public class Ink extends G.PL implements I.Show {

  //every time things are going into this one buffer.
  public static Buffer BUFFER = new Buffer();

  public Ink() {
    super(BUFFER.n);
    for (int i = 0; i < BUFFER.n; i++) {
      this.points[i].set(BUFFER.points[i]);
    }
    G.V.T.set(Ink.BUFFER.bbox,new G.VS(100,100,100,100));
    this.transform();
  }

  public void show(Graphics g) {
    g.setColor(Color.black);
    draw(g);
  }

  public static class List extends ArrayList<Ink> implements I.Show {

    public void show(Graphics g) {
      for (Ink ink : this) {
        ink.show(g);
      }
    }
  }

  public static class Buffer extends G.PL implements I.Show, I.Area {

    public static final int MAX = UC.inkBufferMax;
    public int n = 0;
    public G.BBox bbox = new G.BBox();
    //makes this Buffer class private and no other class can call it.
    private Buffer() {
      super(MAX);
    }
    public G.PL subSample(int k){
      G.PL res = new G.PL(k);
      for (int i  = 0; i < k; i++) {
        res.points[i].set(this.points[i * (n - 1) / (k - 1)]);
      }
      return res;
    }

    @Override
    public void show(Graphics g) {
      g.setColor((Color.green));
      drawN(g, n);
      drawNdots(g, n);
      if (n > 0) {
        G.PL ss = subSample(UC.normSampleSize);
        g.setColor(Color.yellow);
        ss.drawNdots(g, UC.normSampleSize);
        ss.draw(g);
        g.setColor(Color.blue);
        bbox.draw(g);
      }
    }

    public void add(int x, int y) {
      if (n < MAX) {
        points[n].set(x, y);
        n++;
        bbox.add(x, y);
      }
    }

    public void clear() {
      n = 0;
    }

    @Override
    public boolean hit(int x, int y) {
      return true;
    }

    @Override
    public void dn(int x, int y) {
      clear();
      add(x, y);
      bbox.set(x, y);
    }

    @Override
    public void drag(int x, int y) {
      add(x, y);
    }

    @Override
    public void up(int x, int y) {

    }


  }
}
