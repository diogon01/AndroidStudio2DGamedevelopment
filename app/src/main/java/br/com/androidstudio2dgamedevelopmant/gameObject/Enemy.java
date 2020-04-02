package br.com.androidstudio2dgamedevelopmant.gameObject;

import android.content.Context;

import androidx.core.content.ContextCompat;

import br.com.androidstudio2dgamedevelopmant.GameLoop;
import br.com.androidstudio2dgamedevelopmant.R;

/**
 * Enemy is a character which always moves in direction of player
 */
public class Enemy extends Circle {

    public static final double SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND * 0.6;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static double SPAWNS_PER_MINUTE = 20;
    private static double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0;
    private static double UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;
    private static double updatesUntilNextSpawn;
    private final Player player;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius);

        this.player = player;
    }

    public Enemy(Context context, Player player) {
        super(
                context,
                ContextCompat.getColor(context, R.color.enemy),
                Math.random() * 1000,
                Math.random() * 1000,
                30
        );
        this.player = player;
    }

    /**
     * readyToSpawn checks if new should spawn, according to the decided num of spawns
     * per minute (SPAWNS_PER_MINUTE at loop)
     *
     * @return
     */
    public static boolean readyToSpawn() {
        if (updatesUntilNextSpawn <= 0) {
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        } else {
            updatesUntilNextSpawn--;
            return false;
        }
    }


    @Override
    public void update() {

        // Calculate vector from enemy to player (in X and Y)
        double distanceToPlayerX = this.player.getPositionX() - this.positionX;
        double distanceToPlayerY = this.player.getPositionY() - this.positionY;

        // Calculate (absolute) distance between enemy (this) and player
        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this, this.player);

        // Calculate direction from enemy to player
        double directionX = distanceToPlayerX / distanceToPlayer;
        double directionY = distanceToPlayerY / distanceToPlayer;

        // Set velocity in the direction to the player
        if (distanceToPlayer > 0) {
            velocityX = directionX * MAX_SPEED;
            velocityY = directionY * MAX_SPEED;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        // Update position of the enemy
        positionX += velocityX;
        positionY += velocityY;


    }
}
