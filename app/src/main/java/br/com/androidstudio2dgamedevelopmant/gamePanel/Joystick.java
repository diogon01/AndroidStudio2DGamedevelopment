package br.com.androidstudio2dgamedevelopmant.gamePanel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import br.com.androidstudio2dgamedevelopmant.Utils;

public class Joystick {

    private final Paint outerCirclePaint;
    private final Paint innerCirclePaint;
    private int innerCircleRadius;
    private int outerCircleRadius;
    private int outerCircleCenterPositionX;
    private int outerCircleCenterPositionY;
    private int innerCircleCenterPositionX;
    private int innerCircleCenterPositionY;
    private double joystickCenterToTouchDistance;
    private boolean isPressed;
    private double actuatorX;
    private double actuatorY;

    public Joystick(int centerPositionX, int centerPositionY,
                    int outerCircleRadius, int innerCircleRadius) {

        // Outer and inner circle make up the joystick
        this.outerCircleCenterPositionX = centerPositionX;
        this.outerCircleCenterPositionY = centerPositionY;
        this.innerCircleCenterPositionX = centerPositionX;
        this.innerCircleCenterPositionY = centerPositionY;

        // Radius of circles
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        // Paint of circles
        this.outerCirclePaint = new Paint();
        this.outerCirclePaint.setColor(Color.GRAY);
        this.outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // Paint of circles
        this.innerCirclePaint = new Paint();
        this.innerCirclePaint.setColor(Color.BLUE);
        this.innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void draw(Canvas canvas) {
        // Draw outer circle
        canvas.drawCircle(
                outerCircleCenterPositionX,
                outerCircleCenterPositionY,
                outerCircleRadius,
                outerCirclePaint
        );

        // Draw inner circle
        canvas.drawCircle(
                innerCircleCenterPositionX,
                innerCircleCenterPositionY,
                innerCircleRadius,
                innerCirclePaint
        );
    }

    public void update() {
        this.updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX * outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY * outerCircleRadius);

    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {
        this.joystickCenterToTouchDistance = Utils.getDistanceBetweenPoints(
                this.outerCircleCenterPositionX,
                this.outerCircleCenterPositionY,
                touchPositionX,
                touchPositionY
        );
        return this.joystickCenterToTouchDistance < outerCircleRadius;
    }

    public boolean setIsPressed(boolean isPressed) {
        return this.isPressed = isPressed;
    }

    public boolean getIsPressed() {
        return this.isPressed;
    }

    public void setActuator(double touchPositionX, double touchPositionY) {
        double deltaX = touchPositionX - this.outerCircleCenterPositionX;
        double deltaY = touchPositionY - this.outerCircleCenterPositionY;
        double deltaDistance = Utils.getDistanceBetweenPoints(0, 0, deltaX, deltaY);

        if (deltaDistance < this.outerCircleRadius) {
            this.actuatorX = deltaX / this.outerCircleRadius;
            this.actuatorY = deltaY / this.outerCircleRadius;
        } else {
            this.actuatorX = deltaX / deltaDistance;
            this.actuatorY = deltaY / deltaDistance;
        }
    }

    public void resetActuator() {
        this.actuatorX = 0.0;
        this.actuatorY = 0.0;
    }

    public double getActuatorX() {
        return this.actuatorX;
    }

    public double getActuatorY() {
        return this.actuatorY;
    }
}
