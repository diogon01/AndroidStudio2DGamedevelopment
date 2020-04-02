package br.com.androidstudio2dgamedevelopmant.gameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Circle is an abstract class witch implements a draw method from a GameObject
 */
public abstract class Circle extends GameObject {

    protected double radius;
    protected Paint paint;

    public Circle(Context context, int color, double positionX, double positionY, double radius) {
        super(positionX, positionY);
        this.radius = radius;
        // Set color of circles
        this.paint = new Paint();
        this.paint.setColor(color);
    }

    /**
     * isColliding checks if two circle objects are colliding, based on their positions and radius
     *
     * @param obCircle1
     * @param obCircle2
     * @return
     */
    public static boolean isColliding(Circle obCircle1, Circle obCircle2) {
        double distance = getDistanceBetweenObjects(obCircle1, obCircle2);
        double distanceToCollision = obCircle1.getRadius() + obCircle2.getRadius();
        if (distance < distanceToCollision)
            return true;
        else
            return false;
    }

    private double getRadius() {
        return this.radius;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);
    }
}
