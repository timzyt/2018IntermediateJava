package marlin.sandbox;

import java.awt.Graphics;

public interface I {

  public interface Show {

    public void show(Graphics g);
  }

  public interface Area {

    public boolean hit(int x, int y);

    public void dn(int x, int y);

    public void drag(int x, int y);

    public void up(int x, int y);

  }


}
