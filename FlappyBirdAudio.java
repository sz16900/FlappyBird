import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.*;
import javafx.util.Duration;

public class FlappyBirdAudio {

  public void backgroundPlay() {
    // Sound Credit to:
    // https://www.playonloop.com/2016-music-loops/chubby-cat/
    URL url = getClass().getResource("chubby-cat.wav");
    MediaPlayer mp = new MediaPlayer(new Media(url.toString()));
    // This is to loop the audio until player quits the game
    mp.setOnEndOfMedia(new Runnable() {
          public void run() {
            mp.seek(Duration.ZERO);
          }
      });
     mp.play();
  }

}
