package br.com.androidstudio2dgamedevelopmant.gameObject;

import android.content.Context;

import androidx.core.content.ContextCompat;

import br.com.androidstudio2dgamedevelopmant.GameLoop;
import br.com.androidstudio2dgamedevelopmant.R;

public class Spell extends Circle {

    public static final double SPEED_PIXELS_PER_SECOND = 800.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    public Spell(Context context, Player spellCaster) {
        super(
                context,
                ContextCompat.getColor(context, R.color.spell),
                spellCaster.getPositionX(),
                spellCaster.getPositionY(),
                25
        );

        this.velocityX = spellCaster.getDirectionX() * MAX_SPEED;
        this.velocityY = spellCaster.getDirectionY() * MAX_SPEED;
    }

    @Override
    public void update() {
        this.positionX = this.positionX + velocityX;
        this.positionY = this.positionY + velocityY;
    }
}
