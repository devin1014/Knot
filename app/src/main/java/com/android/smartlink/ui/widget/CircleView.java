package com.android.smartlink.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.android.smartlink.R;

/**
 * User: LIUWEI
 * Date: 2017-10-27
 * Time: 11:22
 */
public class CircleView extends android.support.v7.widget.AppCompatImageView
{
    private Paint mPaint = new Paint();

    private int mProgressColor;

    private int mBorderColor;

    private int mCircleBackgroundColor;

    private int mProgress;

    public CircleView(Context context)
    {
        super(context);

        initialize(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        initialize(context, attrs);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        initialize(context, attrs);
    }

    private void initialize(Context context, @Nullable AttributeSet attrs)
    {
        mPaint.setAntiAlias(true);

        if (attrs != null)
        {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);

            setCircleBorderColor(a.getColor(R.styleable.CircleView_circleBorderColor, 0x00000000));

            setCircleBorderWidth(a.getDimensionPixelSize(R.styleable.CircleView_circleBorderWidth, 5));

            setCircleProgressColor(a.getColor(R.styleable.CircleView_circleProgressColor, 0x00000000));

            setCircleProgress(a.getInteger(R.styleable.CircleView_circleProgress, 0));

            setCircleBackgroundColor(a.getColor(R.styleable.CircleView_circleBackgroundColor, 0x00000000));

            a.recycle();
        }
    }

    public void setCircleBorderColor(int color)
    {
        mBorderColor = color;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public void setCircleBorderWidth(int width)
    {
        setPadding(width, width, width, width);

        mPaint.setStrokeWidth(width);
    }

    public void setCircleProgressColor(int color)
    {
        mProgressColor = color;
    }

    public void setCircleProgress(int progress)
    {
        mProgress = Math.min(Math.max(progress, 0), 100);

        invalidate();
    }

    public void setCircleBackgroundColor(int color)
    {
        mCircleBackgroundColor = color;
    }

    private RectF mRectF;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        int centerX = getWidth() / 2;

        int centerY = getHeight() / 2;

        int radius = getWidth() / 2 - (int) mPaint.getStrokeWidth();

        mRectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        int centerX = getWidth() / 2;

        int centerY = getHeight() / 2;

        int radius = getWidth() / 2 - (int) mPaint.getStrokeWidth();

        // background
        mPaint.setStyle(Style.FILL);

        mPaint.setColor(mCircleBackgroundColor);

        canvas.drawCircle(centerX, centerY, radius, mPaint);

        // border
        mPaint.setStyle(Style.STROKE);

        mPaint.setColor(mBorderColor);

        canvas.drawCircle(centerX, centerY, radius, mPaint);

        // progress

        mPaint.setColor(mProgressColor);

        canvas.drawArc(mRectF, -90, mProgress / 100f * 360, false, mPaint);
    }
}
