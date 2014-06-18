package nl.tue.roboticslab.simplepaint.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by mmartin on 6-5-14.
 */
public class DrawableView extends View {
    private Path path;
    private Paint paint;
    private int colour = 0xFF660000;
    private Paint canvasPaint;
    private Bitmap canvasBitmap;
    private Canvas canvas;
    private DrawableView view;

    public DrawableView(Context context) {
        super(context);
        this.setupDrawing();
    }

    public DrawableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setupDrawing();
    }

    public DrawableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setupDrawing();
    }

    private void setupDrawing() {
        path = new Path();
        paint = new Paint();

        paint.setColor(colour);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);

        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //detect user touch
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(path, paint);
                path.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void setColour(String newColour) {
        // Set colour
        invalidate();

        colour = Color.parseColor(newColour);
        paint.setColor(colour);
    }
}
