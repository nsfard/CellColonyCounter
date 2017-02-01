package com.example.nsfard.cellcolonycounter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

/**
 * Created by nsfard on 12/4/16.
 */
public class ZoomImageView extends ImageView {
    private static final int INVALID_POINTER_ID = -1;
    private float posX;
    private float posY;
    private float lastTouchX;
    private float lastTouchY;
    private float lastGestureX;
    private float lastGestureY;
    private int activePointerId = INVALID_POINTER_ID;
    private ScaleGestureDetector scaleDetector;
    private GestureDetector detector;
    private float scaleFactor = 1.f;
    private float imageWidth;
    private float imageHeight;
    private RectF displayRect;
    private float initialScaleFactor;

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        scaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        displayRect = new RectF(0, 0, getMaxWidth(), getMaxHeight());
        detector = new GestureDetector(getContext(), new GestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        scaleDetector.onTouchEvent(ev);
        detector.onTouchEvent(ev);

        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                if (!scaleDetector.isInProgress()) {
                    final float x = ev.getX();
                    final float y = ev.getY();

                    lastTouchX = x;
                    lastTouchY = y;
                    activePointerId = ev.getPointerId(0);
                }
                break;
            }


            case MotionEvent.ACTION_MOVE: {

                // Only move if the ScaleGestureDetector isn't processing a gesture.
                if (!scaleDetector.isInProgress()) {
                    final int pointerIndex = ev.findPointerIndex(activePointerId);
                    final float x = ev.getX(pointerIndex);
                    final float y = ev.getY(pointerIndex);

                    final float dx = x - lastTouchX;
                    final float dy = y - lastTouchY;

                    final float newX = posX + dx;
                    final float newY = posY + dy;
                    final float newHeight = imageHeight * scaleFactor /.3f;
                    final float newWidth = imageWidth * scaleFactor / .3f;

                    posX += dx;
                    posY += dy;

//                    RectF drawingRect = new RectF(newX, newY, newX + newWidth, newY + newHeight);
//
//                    float diffUp = Math.min(displayRect.bottom - drawingRect.bottom, displayRect.top - drawingRect.top);
//                    float diffDown = Math.max(displayRect.bottom - drawingRect.bottom, displayRect.top - drawingRect.top);
//                    float diffLeft = Math.min(displayRect.left - drawingRect.left, displayRect.right - drawingRect.right);
//                    float diffRight = Math.max(displayRect.left - drawingRect.left, displayRect.right - drawingRect.right);
//
//                    if (diffUp > 0) {
//                        posY += diffUp;
//                    }
//                    if (diffDown < 0) {
//                        posY += diffDown;
//                    }
//                    if (diffLeft > 0) {
//                        posX += diffLeft;
//                    }
//                    if (diffRight < 0) {
//                        posX += diffRight;
//                    }

                    invalidate();

                    lastTouchX = x;
                    lastTouchY = y;
                }
                else{
                    final float gx = scaleDetector.getFocusX();
                    final float gy = scaleDetector.getFocusY();

                    final float gdx = gx - lastGestureX;
                    final float gdy = gy - lastGestureY;

                    posX -= gdx;
                    posY -= gdy;

                    invalidate();

                    lastGestureX = gx;
                    lastGestureY = gy;
                }

                break;
            }
            case MotionEvent.ACTION_UP: {
                activePointerId = INVALID_POINTER_ID;
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                activePointerId = INVALID_POINTER_ID;
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {

                final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == activePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    lastTouchX = ev.getX(newPointerIndex);
                    lastTouchY = ev.getY(newPointerIndex);
                    activePointerId = ev.getPointerId(newPointerIndex);
                }
                else{
                    final int tempPointerIndex = ev.findPointerIndex(activePointerId);
                    lastTouchX = ev.getX(tempPointerIndex);
                    lastTouchY = ev.getY(tempPointerIndex);
                }

                break;
            }
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.save();

        canvas.translate(posX, posY);

        if (scaleDetector.isInProgress()) {
            canvas.scale(scaleFactor, scaleFactor, scaleDetector.getFocusX(), scaleDetector.getFocusY());
        }
        else{
            canvas.scale(scaleFactor, scaleFactor, lastGestureX, lastGestureY);
        }

        super.onDraw(canvas);
        canvas.restore();
    }

    public void initImage() {
        posX = 0;
        posY = 0;
        lastTouchX = 0;
        lastTouchY = 0;
        scaleFactor = initialScaleFactor;
        lastGestureX = 0;
        lastGestureY = 0;
        imageHeight = getMaxHeight();
        imageWidth = getMaxWidth();
        invalidate();
    }

    public void setInitialScaleFactor(float scaleFactor) {
        this.initialScaleFactor = scaleFactor;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            scaleFactor = Math.max(0.3f, Math.min(scaleFactor, 10.0f));

            invalidate();
            return true;
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            initImage();
            return true;
        }
    }
}
