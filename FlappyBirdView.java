//TO DO:
  // Like MIKU's program, maybe it can get stuck between the columns whiles the timeline still running.
  // At this point, I feel like I could start to use a controller class. Lets see what goes on.
  // For the report, shall I include this? Maybe, yes, for sure. Things need to be documented;
  // SO: yesterday i had a model-view idea. Today it feels more like a controller somewhere
  // One example for the controller is in the Collision class, instead of returning a boolean
  // it could return a gameover to stop the time. GameOver is used multiple times.

import java.util.ArrayList;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
  int ymotion, ticks;
  Timeline tim = new Timeline();
  IntegerStringConverter str;
  int X, Y;
  ArrayList<Rectangle> columns;
  Columns columnsObject = new Columns(H, W);
  boolean gameOver = false;
  Button btn;
  FlappyBirdModel model = new FlappyBirdModel();
  Stage primaryStage = new Stage();
  Bird birdObject = new Bird(H, W);
  Ground groundObject = new Ground(H, W);
  Rectangle ground;

  @Override
  public void start (Stage stage){
    primaryStage = stage;
    setupStage();

    // str = new IntegerStringConverter();

    root.getChildren().addAll(columns = columnsObject.getColums());
    root.getChildren().add(ground = groundObject.getGround());
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

  private void listen(ActionEvent e) {

//   if(gameOver == true){
//     btn = new Button();
//     btn.setText("Restart");
//     btn.setTranslateX(350);
//     btn.setTranslateY(600);
//     btn.setPrefSize(200, 50);
//     btn.setTextFill(Color.BLUE);
//     btn.setFont(new Font("Arial", 20));
//     root.getChildren().add(btn);
//     root.getChildren().removeAll(columns);
//     root.getChildren().remove(bird);
//     btn.setOnMouseClicked(k ->
//     {
//       root.getChildren().remove(btn);
//       tim.play();
//       gameOver = false;
//       bird.setCenterX(W / 2 - 10);
//       bird.setCenterY(H / 2 - 10);
//       root.getChildren().add(bird);
//     });
//   }


    // Keep falling until key is pressed and realeased
    bird.setCenterY(this.model.gravity((int)bird.getCenterY()));

//
    for(int i = 0; i < columns.size(); i++) {
      Rectangle column = columns.get(i);
      column.setX((column.getX()-5));
    }

    if(model.collision(bird, columns)) {
      tim.pause();
    }

    // Has anybody pressed and realead the a key? If so, Jump
    scene.setOnKeyReleased(k -> {
    String code = k.getCode().toString();
      if(code == "UP") {
        // seems like I am doing this twice. However, it works for now, so lets move on
        bird.setCenterY((int)bird.getCenterY() + this.model.Jump());
      }
    });

    }

}
