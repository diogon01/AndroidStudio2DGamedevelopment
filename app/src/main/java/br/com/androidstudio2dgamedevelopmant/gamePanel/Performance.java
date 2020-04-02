package br.com.androidstudio2dgamedevelopmant.gamePanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import br.com.androidstudio2dgamedevelopmant.GameLoop;
import br.com.androidstudio2dgamedevelopmant.R;

public class Performance {
    private final Context context;
    private GameLoop gameLoop;

    public Performance(Context context, GameLoop gameLoop) {
        this.context = context;
        this.gameLoop = gameLoop;

    }

    public void draw(Canvas canvas) {
        this.drawUPS(canvas);
        this.drawFPS(canvas);
    }

    public void drawUPS(Canvas canvas) {

        String averageUPS = Double.toString(this.gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS" + averageUPS, 100, 100, paint);
    }

    public void drawFPS(Canvas canvas) {

        String averageFPS = Double.toString(this.gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS" + averageFPS, 100, 200, paint);
    }
}
