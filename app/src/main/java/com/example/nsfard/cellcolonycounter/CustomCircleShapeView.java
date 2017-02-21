package com.example.nsfard.cellcolonycounter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by nsfard on 2/15/17.
 */
public class CustomCircleShapeView extends View {
    private  Context context;
    private Paint paint;
//    private SurfaceHolder holder;

    public CustomCircleShapeView(Context context) {
        super(context);

        setUp(context);
    }

    public CustomCircleShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setUp(context);
    }

    public CustomCircleShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setUp(context);
    }

    public CustomCircleShapeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        setUp(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas = holder.lockCanvas();
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawColor(Color.TRANSPARENT);
//        canvas.drawRect(getX(), 50, 200, 200, paint);
//        canvas.drawCircle(getX() + getWidth()/2, getY() + getHeight()/2, getWidth()/2, paint);
        canvas.drawCircle(getWidth()/2,getWidth()/2 + 20,getWidth()/2, paint);
//        holder.unlockCanvasAndPost(canvas);

    }

    private void setUp(Context context) {
        this.context = context;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        invalidate();
    }
}
