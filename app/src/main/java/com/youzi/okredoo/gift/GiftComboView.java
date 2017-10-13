package com.youzi.okredoo.gift;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.youzi.okredoo.util.WidgetUtil;


/**
 * 送礼连击按钮
 */
public class GiftComboView extends AppCompatTextView {

    private Paint paint;

    private RectF rect;

    private float sweepAngle = 360F;

    private int p;

    public GiftComboView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = WidgetUtil.dip2px(context, 3);
        paint = new Paint();
        paint.setColor(0xFFFFFABB);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(p);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        rect = new RectF();
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
        invalidate();
    }

    public void reset() {
        setSweepAngle(360);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        rect.left = rect.top = p;
        rect.right = getMeasuredWidth() - p;
        rect.bottom = getMeasuredHeight() - p;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rect, 270 - sweepAngle, sweepAngle, false, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setScaleX(1.2f);
                setScaleY(1.2f);
                break;
            case MotionEvent.ACTION_UP:
                setScaleX(1f);
                setScaleY(1f);
                break;
        }
        return super.onTouchEvent(event);
    }
}
