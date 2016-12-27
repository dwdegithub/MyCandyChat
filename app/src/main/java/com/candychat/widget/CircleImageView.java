package com.candychat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ZN_mager on 2016/5/11.
 */
public class CircleImageView extends ImageView {
    private Path mCirclePath = new Path();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private Paint mLayerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        mLayerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        resetCirclePath();
    }

    private void resetCirclePath() {
        float radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2f;
        mCirclePath.reset();
        mCirclePath.addCircle(getMeasuredWidth() / 2f, getMeasuredHeight() / 2f, radius, Path.Direction.CCW);
    }


    @Override
    public void draw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawPath(mCirclePath, mPaint);
        canvas.saveLayer(0, 0, width, height, mLayerPaint, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
        canvas.restore();
    }
}
