package br.com.androidstudio2dgamedevelopmant.gamePanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import br.com.androidstudio2dgamedevelopmant.R;

/**
 * Game over is a panel witch draws the text Game Over to screen
 */
public class GameOver {

    private final Context context;

    public GameOver(Context context) {
        this.context = context;
    }

    public void draw(Canvas canvas) {

        float x = 600;
        float y = 200;

        Paint paint = new Paint();

        float textSize = 80;
        int color = ContextCompat.getColor(context, R.color.gameOver);
        paint.setColor(color);
        paint.setTextSize(textSize);
        canvas.drawText(context.getString(R.string.game_over), x, y, paint);
    }
}
