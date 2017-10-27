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
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-27
 * Time: 11:22
 */
public class CircleView extends android.support.v7.widget.AppCompatImageView
{
    private Paint mBorderPaint = new Paint();

    private Paint mProgressPaint = new Paint();

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
        if (attrs != null)
        {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);

            setBorderColor(a.getColor(R.styleable.CircleView_border_color, 0x00000000));

            setBorderWidth(a.getDimensionPixelSize(R.styleable.CircleView_border_width, 5));

            setProgressColor(a.getColor(R.styleable.CircleView_progressColor, 0x00000000));

            setProgress(a.getInteger(R.styleable.CircleView_progress, 0));

            a.recycle();
        }
    }

    public void setBorderColor(int color)
    {
        mBorderPaint.setColor(color);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public void setBorderWidth(int width)
    {
        setPadding(width, width, width, width);

        mBorderPaint.setStrokeWidth(width);

        mBorderPaint.setStyle(Style.STROKE);

        mProgressPaint.setStrokeWidth(width);

        mProgressPaint.setStyle(Style.STROKE);
    }

    public void setProgressColor(int color)
    {
        mProgressPaint.setColor(color);
    }

    public void setProgress(int progress)
    {
        mProgress = Math.min(Math.max(progress, 0), 100);

        invalidate();
    }

    private RectF mRectF;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        int centerX = getWidth() / 2;

        int centerY = getHeight() / 2;

        int radius = getWidth() / 2 - (int) mBorderPaint.getStrokeWidth();

        mRectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        int centerX = getWidth() / 2;

        int centerY = getHeight() / 2;

        int radius = getWidth() / 2 - (int) mBorderPaint.getStrokeWidth();

        canvas.drawCircle(centerX, centerY, radius, mBorderPaint);

        canvas.drawArc(mRectF, -90, mProgress / 100f * 360, false, mProgressPaint);
    }
}
