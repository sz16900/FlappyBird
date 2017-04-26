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
  Group root;
  Ellipse bird, bird2;
  int ymotion, ticks;
  Timeline tim = new Timeline();
  IntegerStringConverter str;
  int X, Y;
  ArrayList<Rectangle> columns;
  boolean gameOver = false;
  Button btn;
  FlappyBirdModel model = new FlappyBirdModel();



  @Override
  public void start (Stage stage){

    stage.setTitle("Flappy Bird");
    stage.setHeight(H);
    stage.setWidth(W);
    // I don't want the user to resize the window. Window is has set dimension
    stage.setResizable(false);

    // str = new IntegerStringConverter();

    root = new Group();

    bird = new Ellipse();
    // fill this ellipse with the initialized image
    bird.setFill(Color.DARKGREEN);
    bird.setRadiusX(20);
    bird.setRadiusY(20);
    bird.setCenterX(W / 2 - 10);
    bird.setCenterY(H / 2 - 10);

    root.getChildren().addAll(bird);
    scene = new Scene(root);



    // columns = new ArrayList<Rectangle>();
    // int i = 0;
    // while(i < 20) {
    //   addColumn();
    //   i++;
    // }
    // root.getChildren().addAll(columns);

    stage.setScene(scene);
    stage.show();


    tim.setCycleCount(Animation.INDEFINITE);
    KeyFrame kf = new KeyFrame(Duration.millis(20), this::listen);
    tim.getKeyFrames().add(kf);
    tim.play();



  }

private void listen(ActionEvent e) {
//
//   if(bird.getCenterY() > H - 120 || bird.getCenterY() < 0) {
//     tim.stop();
//     gameOver = true;
//   }

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
    int y = (int)bird.getCenterY();
    bird.setCenterY(model.gravity(y));
//
//     for(int i = 0; i < columns.size(); i++) {
//       Rectangle column = columns.get(i);
//       column.setX((column.getX()-5));
//     }
//

    // Has anyoneone pressed and realead the a key? If so, Jump
    scene.setOnKeyReleased(k -> {
    String code = k.getCode().toString();
      if(code == "UP") {
        // seems like I am doing this twice. However, it works for now, so lets move on
        bird.setCenterY(y + model.Jump());
      }
    });
//
    }
//

//
//     void addColumn() {
//
//       int space = 400;
//       int width = 100;
//       int height = 50 + (int)(Math.random()*300);
//
//       columns.add(new Rectangle(W + width + (columns.size() * 200), H - height - 120, width, height));
//       columns.add(new Rectangle(W + width + (columns.size() - 1) * 200, 0, width, H - height - space));
//
    // }


}
