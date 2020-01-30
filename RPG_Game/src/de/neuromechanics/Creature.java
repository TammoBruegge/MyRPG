package de.neuromechanics;

import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class Creature extends Entity {
  protected int health;
  protected int speed;
  protected int xMove, yMove;
  private Level level;
  SpriteSheet spriteSheet;

  public Creature(String name, Level level, SpriteSheet spriteSheet, int x, int y, int width, int height, int health, int speed) {
    super(name, spriteSheet.getSpriteElement(0, 1), x, y, width, height);
    this.level = level;
    this.spriteSheet = spriteSheet;
    this.health = health;
    this.speed = speed;
    xMove = 0;
    yMove = 0;
  }

  int op = 1;
  int slow = 0;
  int xPos = 0;
  private int oldX;
  private int oldY;
  public void move(){
    oldX = entityX;
    oldY = entityY;
    entityX += xMove * speed;
    entityY += yMove * speed;

    int[][] touched = level.getTilesTouched(this);
    for(int i = 0; i < touched.length; i++) {
      if(Utils.containsBlock(touched)) {
        entityX = oldX;
        entityY = oldY;
      }
    }

    if(slow++ >= 7) {
      if(xMove == 0 && yMove == 0) {
        slow = 7;
        setCurrentImage(0, 0, 0);
      } else {
        slow = 0;
        if(op == -1 && xPos <= 0) {
          op = 1;
        } else if(op == 1 && xPos >= 2) {
          op = -1;
        }
        xPos = (xPos + op);
        setCurrentImage(xMove, yMove, xPos);
      }
    }
  }
  int prevDirection;
  BufferedImage image;
  void setCurrentImage(int x, int y, int xPos) {
    if(y == -1) {
      image = spriteSheet.getSpriteElement(xPos, 3);
      prevDirection = 3;
    } else if(y == 1) {
      image = spriteSheet.getSpriteElement(xPos, 0);
      prevDirection = 0;
    } else if(x == -1) {
      image = spriteSheet.getSpriteElement(xPos, 1);
      prevDirection = 1;
    } else if(x == 1) {
      image = spriteSheet.getSpriteElement(xPos, 2);
      prevDirection = 2;
    } else {
      image = spriteSheet.getSpriteElement(1, prevDirection);
    }
    setEntityImage(image);
  }
  public void setMove(Point p) {
    xMove = p.x;
    yMove = p.y;
  }
}