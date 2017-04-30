import java.util.ArrayList;
import javafx.scene.shape.Rectangle;

public class Columns {

  private static ArrayList<Rectangle> columns;
  private static int space, width, W, H;

  Columns(int H, int W, int columnSize) {
    columns = new ArrayList<Rectangle>();
    space = 400;
    width = 100;
    this.W = W;
    this.H = H;
    int i = 0;

    while(i < columnSize) {
      addColumn();
      i++;
    }
  }

  private void addColumn() {
    int height = 50 + (int)(Math.random()*300);

    columns.add(new Rectangle(this.W + width + (columns.size() * 200), this.H - height - 120, width, height));
    columns.add(new Rectangle(this.W + width + (columns.size() - 1) * 200, 0, width, this.H - height - space));
  }

  public ArrayList<Rectangle> getColumns() {
    return columns;
  }

}
