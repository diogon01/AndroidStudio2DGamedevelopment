package br.com.androidstudio2dgamedevelopmant;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.androidstudio2dgamedevelopmant.gameObject.Circle;
import br.com.androidstudio2dgamedevelopmant.gameObject.Enemy;
import br.com.androidstudio2dgamedevelopmant.gameObject.Player;
import br.com.androidstudio2dgamedevelopmant.gameObject.Spell;
import br.com.androidstudio2dgamedevelopmant.gamePanel.GameOver;
import br.com.androidstudio2dgamedevelopmant.gamePanel.Joystick;
import br.com.androidstudio2dgamedevelopmant.gamePanel.Performance;

class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    private final Performance performance;
    // private final Enemy enemy;
    private GameLoop gameLoop;
    private Context context;

    // List enemies
    private List<Enemy> enemyList = new ArrayList<>();
    // List spells
    private List<Spell> spellList = new ArrayList<Spell>();
    // Joystick pointer
    private int joystickPointerID = 0;
    private GameOver gameOver;

    public Game(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.gameLoop = new GameLoop(this, surfaceHolder);
        this.context = context;

        // Initialize panels
        this.performance = new Performance(this.context, this.gameLoop);
        this.gameOver = new GameOver(this.context);
        this.joystick = new Joystick(275, 450, 70, 40);

        // Initialize Player
        this.player = new Player(this.context, this.joystick, 2 * 500, 500, 30);


        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Handle touch event actions
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (this.joystick.getIsPressed()) {
                    // Joystick was pressed before this event -> cast spell
                    this.spellList.add(new Spell(getContext(), this.player));
                } else if (this.joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    // Joystick is pressed in this event -> setIsPressed (true) and store ID
                    this.joystickPointerID = event.getPointerId(event.getActionIndex());
                    this.joystick.setIsPressed(true);
                } else {
                    // Joystick was not previously, and is not pressed in this event -> cast spell
                    this.spellList.add(new Spell(getContext(), this.player));

                }
                return true;
            case MotionEvent.ACTION_MOVE:
                // Joystick was pressed previously and is now moved
                if (this.joystick.getIsPressed()) {
                    this.joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (this.joystickPointerID == event.getPointerId(event.getActionIndex())) {
                    // Joystick was let go of -> setIsPressed false (false) and resetActuator
                    this.joystick.setIsPressed(false);
                    this.joystick.resetActuator();
                }

                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.gameLoop.starLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Draw the Player Object
        this.player.draw(canvas);

        // Draw the Joystick Object
        this.joystick.draw(canvas);

        // Draw performance UPS and FPS
        this.performance.draw(canvas);

        // Draw the enemy Object
        for (Enemy enemy : this.enemyList) {
            enemy.draw(canvas);
        }
        // Draw each spell
        for (Spell spell : spellList) {
            spell.draw(canvas);
        }
        // Draw game over if player is dead
        if (this.player.getHealthPoints() <= 0) {
            this.gameOver.draw(canvas);
        }

    }


    public void update() {

        // Stop updating the game if player is dead
        if (player.getHealthPoints() <= 0) {
            return;
        }

        // Call the joystick method of update
        this.joystick.update();
        // Call the player method of update
        this.player.update();

        // Spawn enemy if is time to spawn new enemies
        if (Enemy.readyToSpawn()) {
            this.enemyList.add(new Enemy(getContext(), this.player));
        }

        // Update state of each enemy
        for (Enemy enemy : this.enemyList) {
            enemy.update();
        }

        // Update state of each enemy
        for (Spell spell : this.spellList) {
            spell.update();
        }

        // Iterate through enemyList and check for collision between each enemy and the player
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while (iteratorEnemy.hasNext()) {
            // Get next enemy to the iterator
            Circle enemy = iteratorEnemy.next();
            if (Circle.isColliding(enemy, this.player)) {
                // Remove enemy if it collides with the player
                iteratorEnemy.remove();
                this.player.setHealthPoints(this.player.getHealthPoints() - 1);
                continue;
            }
            // Iterate through spellList and check for collision between each enemy
            Iterator<Spell> iteratorSpell = spellList.iterator();
            while (iteratorSpell.hasNext()) {
                Circle spell = iteratorSpell.next();
                // Remove spell if collides with an enemy
                if (Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
                    break;
                }
            }
        }


    }
}
