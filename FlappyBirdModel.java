import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import java.util.ArrayList;

public class FlappyBirdModel {

  private int ticks, ymotion, W, H;
  private Rectangle collidedColumn = new Rectangle();

  FlappyBirdModel(int W, int H) {
    this.W = W;
    this.H = H;
    this.ticks = 0;
    this.ymotion = 0;
  }

  public int gravity(Ellipse bird) {
    // By having ticks here, one can either speed up the jumo of slow it down
    // depends on how hard one wants the game to be
    this.ticks++;
    if(this.ticks % 2 == 0 && this.ymotion < 15){
      // was 2, lets see what we can do about this
      this.ymotion = this.ymotion + 2;
    }
    return ymotion + (int)bird.getCenterY();
  }

  public int Jump() {
    // Reset the ymotion so the jump is smooth
    if(this.ymotion > 0) {
      this.ymotion = 0;
    }
    this.ymotion = this.ymotion - 10;
    return this.ymotion;
  }

  public boolean collision(Ellipse bird, ArrayList<Rectangle> columns) {
    for(Rectangle column:columns) {
      // Check for each columns collision by finding the intersection of bird
      // and column
      if((column.getBoundsInParent().intersects(bird.getBoundsInParent()))) {
        collidedColumn = column;
        return true;
      }
    }
    // Check for Sky and Ground
    if(bird.getCenterY() > H - 120 || bird.getCenterY() < 0) {
      return true;
    }
    return false;
  }

  public int cloudMove(int X) {
    X = X - 2;
    return X;
  }

  public int cloudRespawn(int X, int imgW) {
    X = W + imgW;
    return X;
  }

  public int columnMove(Rectangle column) {
    return (int)column.getX() - 5;
  }

  public int columnReset(int columnX, int columnTicks) {
    return (columnX + (columnTicks * 5));
  }

}
