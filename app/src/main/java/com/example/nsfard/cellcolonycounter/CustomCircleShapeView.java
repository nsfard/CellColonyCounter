package com.example.nsfard.cellcolonycounter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by nsfard on 2/15/17.
 */
public class CustomCircleShapeView extends View {
    private Bitmap bitmap;
    private Context context;
    private Paint transPaint;
    private Paint blackPaint;
    private Canvas temp;
    private Paint p;
    private Path path;

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

        path.reset();
        path.addCircle(getWidth()/2, getWidth()/2 + 50, getWidth()/2, Path.Direction.CW);
        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);

        canvas.drawCircle(getWidth()/2,getWidth()/2 + 50,getWidth()/2, transPaint);
        canvas.drawPath(path, blackPaint);
        canvas.drawRect(0, 0, getWidth(), 50, blackPaint);
        canvas.drawRect(0, getWidth() + 50, getWidth(), getHeight(), blackPaint);
//        canvas.clipPath(path);
    }

    private void setUp(Context context) {
        this.context = context;

        path = new Path();

        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);

        transPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        transPaint.setColor(Color.TRANSPARENT);
        transPaint.setStyle(Paint.Style.FILL);
        invalidate();
    }
}
