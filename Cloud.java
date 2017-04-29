import javafx.scene.shape.Ellipse;
import javafx.scene.paint.Color;
// import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Cloud {
  private Image img;
  private ImageView imgView;
  private int X, Y;


  Cloud(int W) {
    // Image img = new Image("birdFrame0.png");
    // // convert image into a patter to be pasted onto the ellipse
    // ImagePattern ip = new ImagePattern(img);

    img = new Image("cloud.png");
    imgView = new ImageView(img);
    X = W + (int)img.getWidth();
    imgView.setX(X);
    Y = 10 + (int)(Math.random() * 100);
    imgView.setY(Y);
  }

  public ImageView getCloud() {
    return imgView;
  }

  public int getCloudX() {
    return X;
  }

}
