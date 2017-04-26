public class FlappyBirdModel {

  private int ticks;
  private int ymotion;

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


}
