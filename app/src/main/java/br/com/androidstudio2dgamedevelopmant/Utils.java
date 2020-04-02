package br.com.androidstudio2dgamedevelopmant;

public class Utils {

    /**
     * getDistanceBetweenPoints
     *
     * @param pointX
     * @param pointY
     * @param velocityX
     * @param velocityY
     * @return The distance between 2d points based in X and Y coordinates
     */
    public static double getDistanceBetweenPoints(double pointX, double pointY, double velocityX, double velocityY) {
        return Math.sqrt(
                Math.pow(pointX - velocityX, 2) +
                        Math.pow(pointY - velocityY, 2));
    }
}
