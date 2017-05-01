import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import java.util.ArrayList;

public class FlappyBirdModel {

  private int ticks, birdYmotion, W, H, score, level, columnSize, coinXmotion;
  private Rectangle collidedColumn = new Rectangle();

  FlappyBirdModel(int W, int H) {
    this.W = W;
    this.H = H;
    this.ticks = 0;
    this.birdYmotion = 0;
    this.score = 0;
    this.level = 1;
  }

  public int gravity(Ellipse bird) {
    // By having ticks here, one can either speed up the jumo of slow it down
    // depends on how hard one wants the game to be
    this.ticks++;
    if(this.ticks % 2 == 0 && this.birdYmotion < 15){
      // was 2, lets see what we can do about this
      this.birdYmotion = this.birdYmotion + 2;
    }
    return this.birdYmotion + (int)bird.getCenterY();
  }

  public int Jump() {
    // Reset the ymotion so the jump is smooth
    if(this.birdYmotion > 0) {
      this.birdYmotion = 0;
    }
    this.birdYmotion = this.birdYmotion - 10;
    return this.birdYmotion;
  }

  public boolean collision(Ellipse bird, ArrayList<Rectangle> columns) {
    // Check for Sky and Ground
    if(bird.getCenterY() > H - 120 || bird.getCenterY() < 0) {
      return true;
    }
    for(Rectangle column:columns) {
      // Check for each columns collision by finding the intersection of bird
      // and column
      if((column.getBoundsInParent().intersects(bird.getBoundsInParent()))) {
        collidedColumn = column;
        return true;
      }
    }
    return false;
  }

  public int coinMovement(Ellipse coin, Ellipse bird) {
    int X = (int)coin.getCenterX() - 3;
    return X;
  }

  public boolean coinCollision(Ellipse coin, Ellipse bird) {
    if((coin.getBoundsInParent().intersects(bird.getBoundsInParent()))) {
      this.score = this.score + 100;
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

  public int getScore() {
    this.score = this.score + 1;
    return this.score;
  }

  public int getLevel() {
    this.level = this.level + 1;
    return this.level;
  }

  public void test() {
    Bird birdObject = new Bird(H, W);
    Ellipse bird = birdObject.getBird();
  }

}
