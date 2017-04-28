import java.util.ArrayList;
import javafx.scene.shape.Rectangle;

public class Columns {

  private static ArrayList<Rectangle> columns;

  Columns(int W, int H) {
    columns = new ArrayList<Rectangle>();
    int i = 0;
    while(i < 20) {
      addColumn(W, H);
      i++;
    }
  }

  private void addColumn(int H, int W) {
    int space = 400;
    int width = 100;
    int height = 50 + (int)(Math.random()*300);

    columns.add(new Rectangle(W + width + (columns.size() * 200), H - height - 120, width, height));
    columns.add(new Rectangle(W + width + (columns.size() - 1) * 200, 0, width, H - height - space));
  }

  public ArrayList<Rectangle> getColums() {
    return columns;
  }

}
