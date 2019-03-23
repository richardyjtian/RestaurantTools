package io.github.richardyjtian.tipcalculator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GraphicsView extends View {
    public GraphicsView(Context context) {
        super(context);
    }

    public GraphicsView(Context context, AttributeSet set) {
        super(context);
    }

    Rect rect;

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY); //set background to gray

        Paint paint = new Paint();
        // max_x and max_y of the view
        int max_x = getWidth() - 1;
        int max_y = getHeight() - 1;

        // Draw a white rectangle
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        rect = new Rect(150, 150, max_x-150, max_y-150);
        canvas.drawRect(rect, paint);

        paint.setColor(Color.GRAY);
        paint.setTextSize(200);
        canvas.drawText("CLEAR", max_x/2-300, max_y/2+100, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int Action = event.getAction();

        if(Action == MotionEvent.ACTION_DOWN) {
            // x and y coordinates of the view (relative to view[0,0])
            int x = (int) event.getX();
            int y = (int) event.getY();
            if(rect.contains(x, y)){
                MainActivity.getInstance().clear();
            }
        }
        return true;
    }
}
