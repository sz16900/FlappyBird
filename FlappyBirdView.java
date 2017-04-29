// TO DO: clear the columns once they have exited the screen
//        only 20 columns? what happens if player plays more?
//        insert png into bird + cloud

import java.util.ArrayList;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;

import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import javafx.event.*;
import javafx.scene.input.*;


public class FlappyBirdView extends Application  {

  int W = 800;
  int H = 700;
  int X;
  int columnTicks;
  Scene scene;
  Group root = new Group();
  Ellipse bird = new Ellipse();
  Timeline tim = new Timeline();
  IntegerStringConverter str;
  ArrayList<Rectangle> columns;
  Columns columnsObject = new Columns(H, W);
  boolean gameOver = false;
  FlappyBirdModel model;
  Stage primaryStage = new Stage();
  Bird birdObject = new Bird(H, W);
  Ground groundObject = new Ground(H, W);
  RestartButton button = new RestartButton();
  ImageView cloud;
  Cloud cloudObject = new Cloud(W);

  @Override
  public void start (Stage stage){

    X = cloudObject.getCloudX();


    primaryStage = stage;
    columns = columnsObject.getColums();
    bird = birdObject.getBird();
    cloud = cloudObject.getCloud();
    setupStage();
    root.getChildren().add(groundObject.getGround());
    root.getChildren().add(cloud);

    scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();

    launchTimer();
    mainMenu();
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
    }

    private void birdFall() {
      int gravity = this.model.gravity(bird);
      bird.setCenterY(gravity);
    }

    private void mainMenu() {
      // set the bird back to the center and reset the logic of the game
      bird.setCenterX(W / 2 - 10);
      bird.setCenterY(W / 2 - 10);
      gameOver = false;
      model = new FlappyBirdModel();

      root.getChildren().removeAll(columns);
      root.getChildren().add(bird);

      resetColumns();
      tim.pause();
      scene.setOnKeyReleased(this::pressToStart);
    }

    private void gameOver() {

      gameOver = model.collision(bird, columns);

      if(gameOver) {
        root.getChildren().remove(bird);
        // Make sure this has been added once, else, just listen for click.
        if(!root.getChildren().contains(button.getButton())) {
          root.getChildren().addAll(button.getButton());
        }
        // Listens to the button click to reset the game.
        button.getButton().setOnMouseClicked(this::click);
      }
    }

    private void resetColumns() {
      for(int i = 0; i < columns.size(); i++) {
        Rectangle column = columns.get(i);
        column.setX((column.getX() + (columnTicks * 5)));
      }
      columnTicks = 0;
    }

    private void moveColumns() {
      // Keep track of how many times columms have moved. This will help to
      // reset the columns at gameOver.
      columnTicks++;
      for(int i = 0; i < columns.size(); i++) {
        Rectangle column = columns.get(i);
        column.setX((column.getX()-5));
        // This remove inserts a bug
        // if(column.getX() + column.getWidth() < 0) {
        //   columns.remove(i);
        // }
      }
    }

/*----------------------These are the Listening Events------------------------*/

  private void listen(ActionEvent e) {
    // Is the game over?
    gameOver();
    // Keep falling until key is pressed and realeased
    birdFall();
    // Has anyone pressed the UP key?
    scene.setOnKeyReleased(this::pressUP);
    // Update the columns
    moveColumns();


    X = X - 2;
    cloud.setX(X);
  }

  private void click(MouseEvent event) {
    // Changes the window's layout
    root.getChildren().remove(button.getButton());
    mainMenu();
  }

  private void pressUP(KeyEvent event) {
    String code = event.getCode().toString();
    if(code == "UP") {
      bird.setCenterY((int)bird.getCenterY() + this.model.Jump());
    }
  }

  private void pressToStart(KeyEvent event) {
    String code = event.getCode().toString();
    if(code == "UP"){
      root.getChildren().addAll(columns);
      tim.play();
    }
  }

}
