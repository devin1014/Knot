package com.android.smartlink.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.android.smartlink.R;

/**
 * User: LIUWEI
 * Date: 2017-12-13
 * Time: 16:56
 */
public class CircleViewTablet extends AppCompatImageView
{
    private Paint mPaint = new Paint();

    private int mGradientWidth;

    private int mGradientColor;

    private int mBorderWidth;

    private int mBorderColor;

    private int mColor;

    public CircleViewTablet(Context context)
    {
        super(context);

        initialize(context, null);
    }

    public CircleViewTablet(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        initialize(context, attrs);
    }

    public CircleViewTablet(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        initialize(context, attrs);
    }

    private void initialize(Context context, @Nullable AttributeSet attrs)
    {
        mPaint.setAntiAlias(true);

        if (attrs != null)
        {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleViewTablet);

            setColor(a.getColor(R.styleable.CircleViewTablet_circleColor, 0x00000000));

            setBorderWidth(a.getDimensionPixelSize(R.styleable.CircleViewTablet_circleBorderWidth, 0));

            setGradientWidth(a.getDimensionPixelSize(R.styleable.CircleViewTablet_circleGradientWidth, 0));

            a.recycle();
        }
    }

    public void setColor(int color)
    {
        mColor = color;

        mGradientColor = Color.argb(33, Color.red(mColor), Color.green(mColor), Color.blue(mColor));

        mBorderColor = Color.argb(204, Color.red(mColor), Color.green(mColor), Color.blue(mColor));
    }

    public void setBorderWidth(int width)
    {
        mBorderWidth = width;
    }

    public void setGradientWidth(int width)
    {
        mGradientWidth = width;
    }

    //    private RectF mRectF;
    //
    //    @Override
    //    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    //    {
    //        super.onSizeChanged(w, h, oldw, oldh);
    //
    //        int centerX = getWidth() / 2;
    //
    //        int centerY = getHeight() / 2;
    //
    //        int radius = getWidth() / 2 - (int) mPaint.getStrokeWidth();
    //
    //        mRectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    //    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        int centerX = getWidth() / 2;

        int centerY = getHeight() / 2;

        int radius = getWidth() / 2;

        // background
        mPaint.setStyle(Style.FILL);

        mPaint.setColor(mColor);

        canvas.drawCircle(centerX, centerY, radius - mBorderWidth - mGradientWidth, mPaint);

        // gradient
        mPaint.setStyle(Style.STROKE);

        mPaint.setColor(mGradientColor);

        mPaint.setStrokeWidth(mGradientWidth);

        canvas.drawCircle(centerX, centerY, radius - mBorderWidth - mGradientWidth / 2, mPaint);

        // border
        mPaint.setStyle(Style.STROKE);

        mPaint.setColor(mBorderColor);

        mPaint.setStrokeWidth(mBorderWidth);

        canvas.drawCircle(centerX, centerY, radius - mBorderWidth / 2, mPaint);

        super.onDraw(canvas);
    }
}
