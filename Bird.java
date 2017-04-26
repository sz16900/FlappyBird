import javafx.scene.shape.Ellipse;
import javafx.scene.paint.Color;

public class Bird {

  private Ellipse bird;

  Bird(int H, int W) {
    bird = new Ellipse();
    bird.setFill(Color.DARKGREEN);
    bird.setRadiusX(20);
    bird.setRadiusY(20);
    bird.setCenterX(W / 2 - 10);
    bird.setCenterY(H / 2 - 10);
  }

  public Ellipse getBird() {
    return bird;
  }

}
