package br.com.androidstudio2dgamedevelopmant.gameObject;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import br.com.androidstudio2dgamedevelopmant.GameLoop;
import br.com.androidstudio2dgamedevelopmant.gamePanel.HealthBar;
import br.com.androidstudio2dgamedevelopmant.gamePanel.Joystick;
import br.com.androidstudio2dgamedevelopmant.R;
import br.com.androidstudio2dgamedevelopmant.Utils;

/**
 * Player character inherited from gameObject and circle
 */
public class Player extends Circle {

    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    public static final int MAX_HEALTH_POINTS = 10;
    private final Joystick joystick;
    private HealthBar healthBar;
    private int healthPoints;


    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        this.joystick = joystick;
        this.healthBar = new HealthBar(context, this);
        this.healthPoints = this.MAX_HEALTH_POINTS;

    }

    public void update() {

        // Update velocity based on actuator of joystick
        this.velocityX = this.joystick.getActuatorX() * MAX_SPEED;
        this.velocityY = this.joystick.getActuatorY() * MAX_SPEED;

        // Update position
        this.positionX += this.velocityX;
        this.positionY += this.velocityY;

        // Update direction
        if (this.velocityX != 0 || this.velocityY != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            double distance = Utils.getDistanceBetweenPoints(0, 0, this.velocityX, this.velocityY);
            this.directionX = velocityX / distance;
            this.directionY = velocityY / distance;
        }
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.healthBar.draw(canvas);
    }

    public int getHealthPoints() {
        return this.healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        // Only allow positive values
        if (healthPoints >= 0) {
            this.healthPoints = healthPoints;
        }
    }
}
