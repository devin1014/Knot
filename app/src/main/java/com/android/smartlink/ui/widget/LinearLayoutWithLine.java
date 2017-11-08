package com.android.smartlink.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.android.smartlink.R;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-08
 * Time: 16:20
 */
public class LinearLayoutWithLine extends LinearLayout
{
    private Paint mPaint = new Paint();

    private int mOffsetX;

    public LinearLayoutWithLine(Context context)
    {
        super(context);

        initialize(context, null);
    }

    public LinearLayoutWithLine(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        initialize(context, attrs);
    }

    public LinearLayoutWithLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        initialize(context, attrs);
    }

    @SuppressWarnings("unused")
    public LinearLayoutWithLine(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);

        initialize(context, attrs);
    }

    private void initialize(Context context, @Nullable AttributeSet attrs)
    {
        if (attrs != null)
        {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LinearLayoutWithLine);

            mPaint.setColor(a.getColor(R.styleable.LinearLayoutWithLine_lineColor, Color.parseColor("#ffffff")));

            mPaint.setStrokeWidth(a.getDimensionPixelSize(R.styleable.LinearLayoutWithLine_lineWidth, 2));

            mOffsetX = a.getDimensionPixelOffset(R.styleable.LinearLayoutWithLine_lineOffsetX, 0);

            a.recycle();
        }

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawLine(mOffsetX, 0, mOffsetX, getHeight(), mPaint);

        super.onDraw(canvas);
    }
}
