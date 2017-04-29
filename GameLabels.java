import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class GameLabels {
  private Label scoreLabel;
  private Label menuLabel;

  GameLabels(int W, int H) {
    scoreLabel = new Label();
    scoreLabel.setFont(new Font("Arial", 20));
    scoreLabel.setLayoutY(H - 85);
    menuLabel = new Label();
    menuLabel.setText("Press UP key to start!");
    menuLabel.setFont(new Font("Arial", 50));
    menuLabel.setLayoutX(W / 2 - 250);
    menuLabel.setLayoutY(H / 2 - 100);
    menuLabel.setTextFill(Color.RED);
  }

  public Label getScoreLabel() {
    return this.scoreLabel;
  }

  public Label getMenuLabel() {
    return this.menuLabel;
  }

}
