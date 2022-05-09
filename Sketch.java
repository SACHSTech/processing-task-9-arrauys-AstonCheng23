import processing.core.PApplet;
import processing.core.PFont;

public class Sketch extends PApplet {
	
  float[] circleX = new float[25];
	float[] circleY = new float[25];
  boolean[] circleHideStatus = new boolean[25];

  boolean playerHideStatus = false;

	int x = 400;
  int y = 400;

  double x_speed = 0;
  double y_speed = 0;

  double x_friction = 0;
  double y_friction = 0;

	boolean upPressed = false;
  boolean downPressed = false;
  boolean rightPressed = false;
  boolean leftPressed = false;

  int width = 800;
  int height = 800;

  int fallSpeed = 0;

  int life = 5;
  boolean intangibility = false;
  boolean wasHit = false;

  int intangibilityTimer = 0;

  int fadeWhite = 50;

  PFont deathText;

  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {
	// put your size call here
    size(width, height);
    for (int i = 0; i < circleY.length; i++) {
      circleX[i] = random(width);
      circleY[i] = random(height);
      circleHideStatus[i] = false;
    }
    deathText = createFont("Arial",16,true); 
  }

  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  public void setup() {
    background(210, 255, 173);
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    System.out.println(fallSpeed + " " + life + " " + intangibilityTimer + " " + intangibility);
       
    if (playerHideStatus == false){
      background(50);

      if (upPressed) {
        y_speed -= 3.5;
      }
      if (downPressed) {
        y_speed += 3.5;
      }
      if (leftPressed) {
        x_speed += 3.5;
      }
      if (rightPressed) {
        x_speed -= 3.5;
      }

      if ((x > width) || (x < 0)) {
        x_speed = x_speed * -1.2;
      }
      if ((y > height) || (y < 0)) {
        y_speed = y_speed * -1.2;
      }

      // friction
      if (x_speed > 0) {
        x_friction =  x_speed*0.2;
      }
      if (x_speed < 0) {
        x_friction =  x_speed*0.2;
      }

      if (Math.round(x_speed) == 0) {
        x_friction = 0;
      }


      if (y_speed > 0) {
        y_friction =  y_speed*0.2;
      }
      if (y_speed < 0) {
        y_friction = y_speed*0.2;
      }

      if (Math.round(y_speed)== 0) {
        y_friction = 0;
      }

      x_speed = x_speed - x_friction;
      y_speed = y_speed - y_friction;

      x = (int) (x + (int) x_speed);
      y = (int) (y + (int) y_speed);

      stroke(0);
      fill((float) Math.sin(intangibilityTimer/2)*150, (float) Math.sin(intangibilityTimer/2)*150, 255);
      ellipse(x, y, 15, 15);

      for (int i = 0; i < circleY.length; i++) {
        if (mousePressed == true){
          if ((mouseX > circleX[i] - 15) && (mouseX < circleX[i] + 15) && (mouseY > circleY[i] - 15) && (mouseY < circleY[i] + 15)){
            circleHideStatus[i] = true;
          }
        }
  
        if (circleHideStatus[i] == false){
          //float circleX = width * i / circleY.length;
          stroke(0);
          fill(255);
          ellipse(circleX[i], circleY[i], (float) 25, (float)25);
      
          circleY[i] += fallSpeed;
      
          if (circleY[i] > height) {
            circleY[i] = 0;
          }
          if (circleY[i] < -1) {
            circleY[i] = height;
          }
          if (intangibility == false){
            if ((x > circleX[i] - 15) && (x < circleX[i] + 15) && (y > circleY[i] - 15) && (y < circleY[i] + 15)){
              wasHit = true;
              circleHideStatus[i] = true;
            }
          }
        }
      }
    } else if (playerHideStatus == true) {
      background(fadeWhite);
      if (fadeWhite < 255){
        fadeWhite += 1;
      }
      if (fadeWhite == 255){
        fill(0);
        textFont(deathText, 48);  
        text("YOU ARE DEAD",400, 400);
      }
    }
    
    if (intangibility == false && intangibilityTimer == 0 && wasHit == true){
      intangibilityTimer = 120;
      intangibility = true;
    }
    if (intangibilityTimer > 0){
     intangibilityTimer -= 1;
    }
    if (intangibilityTimer == 0 && intangibility == true){
      intangibility = false;
    }

    if (wasHit == true){
      life -= 1;
      wasHit = false;
    }

    if (life == 0){
      print("you are dead! ");
      playerHideStatus = true;
      
    }

  }
  public void keyPressed(){
    if (key == 119){
      upPressed = true;
    }
    if (key == 115){
      downPressed = true; 
    }
    if (key == 100){
      leftPressed = true;
    }
    if (key == 97){
      rightPressed = true;
    }
    if (key == CODED){
      if (keyCode == UP){
        fallSpeed -= 1;
      }
      if (keyCode == DOWN){
        fallSpeed += 1;
      }
    }
  }
  public void keyReleased() {
    if (key == 119){
      upPressed = false;
    }
    if (key == 115){
      downPressed = false;
    }
    if (key == 100){
      leftPressed = false;
    }
    if (key == 97){
      rightPressed = false;
    }
    if (key == 101){
    }
  }
}