package br.com.androidstudio2dgamedevelopmant.gameObject;

import android.graphics.Canvas;

/**
 * GameObject is an abstract class witch is the foundation off all word objects in the game.
 */
public abstract class GameObject {

    protected double positionX;
    protected double positionY;
    protected double velocityX = 0;
    protected double velocityY = 0;
    protected double directionX = 1;
    protected double directionY = 0;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public abstract void draw(Canvas canvas);

    public abstract void update();

    public double getPositionX() {
        return this.positionX;
    }

    public double getPositionY() {
        return this.positionY;
    }

    protected static double getDistanceBetweenObjects(GameObject object1, GameObject object2) {
        return Math.sqrt(
                Math.pow(object2.getPositionX() - object1.getPositionX(), 2) +
                        Math.pow(object2.getPositionY() - object1.getPositionY(), 2)
        );
    }

    protected double getDirectionX() {
        return this.directionX;
    }

    protected double getDirectionY() {
        return this.directionY;
    }
}
