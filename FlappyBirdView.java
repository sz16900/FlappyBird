import java.util.ArrayList;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import javafx.event.*;

public class FlappyBirdView extends Application  {

  int W = 800;
  int H = 700;
  Scene scene;
  Group root = new Group();
  Ellipse bird = new Ellipse();
  Timeline tim = new Timeline();
  IntegerStringConverter str;
  ArrayList<Rectangle> columns;
  Columns columnsObject = new Columns(H, W);
  boolean gameOver = false;
  FlappyBirdModel model = new FlappyBirdModel();
  Stage primaryStage = new Stage();
  Bird birdObject = new Bird(H, W);
  Ground groundObject = new Ground(H, W);
  RestartButton button = new RestartButton();

  @Override
  public void start (Stage stage){
    primaryStage = stage;
    setupStage();

    // str = new IntegerStringConverter();

    root.getChildren().addAll(columns = columnsObject.getColums());
    root.getChildren().add(groundObject.getGround());
    root.getChildren().addAll(bird = birdObject.getBird());

    scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();

    launchTimer();

    }

    public void setupStage() {
      primaryStage.setTitle("Flappy Bird");
      primaryStage.setHeight(H);
      primaryStage.setWidth(W);
      // I don't want the user to resize the window. Window is has set dimension
      primaryStage.setResizable(false);
    }

    private void launchTimer() {
      tim.setCycleCount(Animation.INDEFINITE);
      KeyFrame kf = new KeyFrame(Duration.millis(20), this::listen);
      tim.getKeyFrames().add(kf);
      tim.play();
    }

    private void birdFall() {
      int axisY = (int)bird.getCenterY();
      int gravity = this.model.gravity(axisY);
      bird.setCenterY(gravity);
    }

    private void gameOver() {

      gameOver = model.collision(bird, columns);

      if(gameOver) {

        // Changes the window's layout
        root.getChildren().add(button.getButton());
        root.getChildren().removeAll(columns);
        root.getChildren().remove(bird);
        tim.pause();

        // Listens to the button click.
        clickButtonRestart();

      }
    }

    private void clickButtonRestart() {
      button.getButton().setOnMouseClicked(k -> {

        // Changes the wondow's layout
        root.getChildren().remove(button.getButton());
        gameOver = false;
        // Bird back in the center
        bird.setCenterX(W / 2 - 10);
        bird.setCenterY(H / 2 - 10);
        root.getChildren().add(bird);
        // Resume
        tim.play();
      });

    }

    private void pressUp() {
      scene.setOnKeyReleased(k -> {
      String code = k.getCode().toString();
        if(code == "UP") {
          // seems like I am doing this twice. However, it works for now, so lets move on
          bird.setCenterY((int)bird.getCenterY() + this.model.Jump());
        }
      });
    }

    private void moveColumns() {
      for(int i = 0; i < columns.size(); i++) {
        Rectangle column = columns.get(i);
        column.setX((column.getX()-5));
      }
    }

  private void listen(ActionEvent e) {

    // Is the game over?
    gameOver();
    // Keep falling until key is pressed and realeased
    birdFall();
    // Has anyone pressed the UP key?
    pressUp();
    // Update the columns
    moveColumns();

  }

}
