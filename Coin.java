import javafx.scene.shape.Ellipse;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;

public class Coin {

  private Ellipse coin;

  Coin(int H, int W) {
    // Image img = new Image("coinFrame0.png");
    // // convert image into a patter to be pasted onto the ellipse
    // ImagePattern ip = new ImagePattern(img);
    coin = new Ellipse();
    coin.setCenterX(600);
    coin.setCenterY(H / 2 - 10);
    // coin.setFill(ip);
    coin.setRadiusX(11);
    coin.setRadiusY(11);
  }

  public Ellipse getCoin() {
    return coin;
  }

}
