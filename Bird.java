import javafx.scene.shape.Ellipse;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;

public class Bird {

  private Ellipse bird;

  Bird(int H, int W) {
    Image img = new Image("birdFrame0.png");
    // convert image into a patter to be pasted onto the ellipse
    ImagePattern ip = new ImagePattern(img);
    bird = new Ellipse();
    bird.setCenterX(W / 2 - 10);
    bird.setCenterY(H / 2 - 10);
    bird.setFill(ip);
    bird.setRadiusX((img.getWidth() / 2) + 2);
    bird.setRadiusY((img.getHeight() / 2) + 2);
  }

  public Ellipse getBird() {
    return bird;
  }

}
