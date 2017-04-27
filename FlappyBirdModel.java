import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import java.util.ArrayList;

public class FlappyBirdModel {

  private int ticks;
  private int ymotion;

  // this needs to be sorted, perhaps when its initialized
  int W = 800;
  int H = 700;

  public int gravity(int birdCenter) {
    this.ticks++;
    if(this.ticks % 2 == 0 && this.ymotion < 15){
      // was 2, lets see what we can do about this
      this.ymotion = this.ymotion + 2;
    }
    return ymotion + birdCenter;

  }

  public int Jump() {
    if(this.ymotion > 0) {
      this.ymotion = 0;
    }
    this.ymotion = this.ymotion - 10;
    return this.ymotion;
  }

  public boolean collision(Ellipse bird, ArrayList<Rectangle> columns) {
    for(Rectangle column:columns) {
      // Check for each columns collision
      if((column.getBoundsInParent().intersects(bird.getBoundsInParent()))) {
        return true;
      }
    }
    // Check for Sky and Ground
    if(bird.getCenterY() > H - 120 || bird.getCenterY() < 0) {
      return true;
    }
    return false;
  }

}
