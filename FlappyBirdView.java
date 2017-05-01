import java.util.ArrayList;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.*;
import javafx.stage.*;
import javafx.event.*;

public class FlappyBirdView extends Application  {

  int W = 800, H = 700, columnSize = 5, X, columnTicks;
  Scene scene;
  Group root = new Group();
  Ellipse bird = new Ellipse();
  Timeline tim = new Timeline();
  IntegerStringConverter str = new IntegerStringConverter();;
  ArrayList<Rectangle> columns;
  Columns columnsObject = new Columns(H, W, columnSize);
  boolean gameOver = false;
  FlappyBirdModel model;
  Stage primaryStage = new Stage();
  Bird birdObject = new Bird(H, W);
  Ground groundObject = new Ground(H, W);
  RestartButton button = new RestartButton();
  ImageView cloud;
  Cloud cloudObject = new Cloud(W);
  GameLabels labelObject = new GameLabels(W, H);
  Label scoreLabel, menuLabel, levelLabel;
  Coin coinObject = new Coin(H, W);
  Ellipse coin = new Ellipse();
  FlappyBirdAudio backgroundAudio = new FlappyBirdAudio();

  @Override
  public void start (Stage stage){
    primaryStage = stage;
    setupStage();
    // They are here and not at mainMenu() because these are never removed
    root.getChildren().add(groundObject.getGround());
    root.getChildren().add(cloud);
    root.getChildren().addAll(scoreLabel);
    root.getChildren().addAll(coin);

    backgroundAudio.backgroundPlay();

    scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();

    launchTimer();
    mainMenu();
    }

    public void setupStage() {
      // Create all of the objects required to run the program
      columns = columnsObject.getColumns();
      bird = birdObject.getBird();
      cloud = cloudObject.getCloud();
      scoreLabel = labelObject.getScoreLabel();
      menuLabel = labelObject.getMenuLabel();
      levelLabel = labelObject.getLevelLabel();
      coin = coinObject.getCoin();

      primaryStage.setTitle("Flappy Bird");
      primaryStage.setHeight(H);
      primaryStage.setWidth(W);
      primaryStage.setResizable(false); // Resizing window not allowed
    }

    // This is the Game's main loop
    private void launchTimer() {
      tim.setCycleCount(Animation.INDEFINITE);
      KeyFrame kf = new KeyFrame(Duration.millis(20), this::listen);
      tim.getKeyFrames().add(kf);
    }

    // Set up the main menu. I.e: either at the beginning or when the player
    // collides with an object in the game
    private void mainMenu() {
      birdObject = new Bird(H, W);
      bird = birdObject.getBird();
      gameOver = false;
      columnSize = 5;
      model = new FlappyBirdModel(W, H);
      scoreLabel.setText("Score:" + " 0" );
      levelLabel.setText( "Level:" + " 1" );
      root.getChildren().removeAll(columns);
      root.getChildren().add(bird);
      root.getChildren().add(menuLabel);
      root.getChildren().add(levelLabel);
      resetColumns();
      tim.pause();
      scene.setOnKeyReleased(this::pressToStart);
    }

    private void gameOver() {
      gameOver = model.collision(bird, columns);
      if(gameOver) {
        root.getChildren().remove(bird);
        root.getChildren().remove(levelLabel);
        // Make sure this has been added once, else, just listen for click.
        if(!root.getChildren().contains(button.getButton())) {
          root.getChildren().addAll(button.getButton());
          birdObject.birdPlay();
        }
        // Listens to the button click to reset the game.
        button.getButton().setOnMouseClicked(this::click);
      }
    }

    private void resetColumns() {
      columnsObject = new Columns(H, W, columnSize);
      columns = new ArrayList<Rectangle>();
      columns = columnsObject.getColumns();
      root.getChildren().addAll(columns);
    }

    private void moveColumns() {
      for(int i = 0; i < columns.size(); i++) {
        Rectangle column = columns.get(i);
        int columnX = model.columnMove(column);
        column.setX(columnX);
        // If this column has gone past the screen, remove it to open up memory
        if(columnX + column.getWidth() == 0) {
          columns.remove(i);
          // If the array of columns is empty, double the array size.
          // This is to increse Level Difficulty
          if(columns.size() == 0) {
            columnSize = columnSize * 2;
            levelLabel.setText("Level:" + " " + str.toString(model.getLevel()));
            resetColumns();
          }
        }
      }
    }

    private void updateScore() {
      if(!gameOver) {
        // Fetch the score from the model. Divide by twenty
        // so that it looks as if it increses slow.
        scoreLabel.setText("Score:" + " " + str.toString(model.getScore()/20));
      }
    }

    private void coinBehaviour() {
      coin.setCenterX(model.coinMovement(coin));
      boolean eraseCoin = model.coinCollision(coin, bird);
      // It shouldnt sound when it leaves the screen
      if(eraseCoin || coin.getCenterX() < 0) {
        root.getChildren().remove(coin);
        coinObject = new Coin(H, W);
        coin = coinObject.getCoin();
        root.getChildren().add(coin);
      }
      // This is so that the coin only makes a sound when bird touches it
      if(eraseCoin) {coinObject.coinPlay();}
    }

    private void updateCloud() {
      int X = (int)cloud.getX();
      // Get the size of the PNG image
      int imgW = cloudObject.getImageWidth();
      X = model.cloudMove(X);

      // Bring the cloud back once it leaves the window.
      if( X < (0 - cloudObject.getImageWidth()) ) {
        X = model.cloudRespawn(X, imgW);
        cloud.setX(X);
      } else { cloud.setX(X); }
    }

/*----------------------These are the Listening Events------------------------*/

// Listen for every single KeyFrame
  private void listen(ActionEvent e) {
    gameOver();
    // Keep falling until key is pressed and realeased
    bird.setCenterY(this.model.gravity(bird));
    // Has anyone pressed the UP key?
    scene.setOnKeyReleased(this::pressUP);
    moveColumns();
    coinBehaviour();
    updateCloud();
    updateScore();
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
      // root.getChildren().addAll(columns);
      root.getChildren().remove(menuLabel);
      tim.play();
    }
  }

}
