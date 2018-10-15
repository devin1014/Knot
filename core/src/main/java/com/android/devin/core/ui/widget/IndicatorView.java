package com.android.devin.core.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;

import com.android.devin.core.R;

/**
 * User: LIUWEI
 * Date: 2017-10-23
 * Time: 15:22
 */
public class IndicatorView extends View
{
    private Paint mPaint = new Paint();

    private Paint mSelectPaint = new Paint();

    private int mIndicatorSize = 4 * 2; // 4dp

    private int mIndicatorDividerSize = mIndicatorSize;

    private int mCount = 0;

    public IndicatorView(Context context)
    {
        super(context);

        initialize(context, null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        initialize(context, attrs);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        initialize(context, attrs);
    }

    private void initialize(Context context, @Nullable AttributeSet attrs)
    {
        if (attrs != null)
        {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);

            setIndicatorColor(a.getColor(R.styleable.IndicatorView_indicatorColor, Color.parseColor("#aaffffff")));

            setIndicatorSelectColor(a.getColor(R.styleable.IndicatorView_indicatorSelectColor, Color.parseColor("#ffffff")));

            setIndicatorSize(a.getDimensionPixelSize(R.styleable.IndicatorView_indicatorSize, mIndicatorSize));

            setIndicatorDividerSize(a.getDimensionPixelSize(R.styleable.IndicatorView_indicatorDividerSize, mIndicatorDividerSize));

            a.recycle();
        }

        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();

        int height = getMeasuredHeight();

        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST)
        {
            if (mCount == 0)
            {
                width = 0;
            }
            else
            {
                width = (mIndicatorDividerSize + mIndicatorSize) * mCount + mIndicatorDividerSize;
            }
        }

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST)
        {
            if (mCount == 0)
            {
                height = 0;
            }
            else
            {
                height = mIndicatorSize + mIndicatorDividerSize * 2;
            }
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();

        if (mViewPager != null)
        {
            mViewPager.removeOnPageChangeListener(mOnPageChangeListener);

            mViewPager = null;
        }
    }

    public void setIndicatorColor(int color)
    {
        mPaint.setColor(color);
    }

    public void setIndicatorSelectColor(int color)
    {
        mSelectPaint.setColor(color);
    }

    public void setIndicatorSize(int size)
    {
        mIndicatorSize = size;
    }

    public void setIndicatorDividerSize(int size)
    {
        mIndicatorDividerSize = size;
    }

    public void setCount(int count)
    {
        if (mCount != count)
        {
            mCount = count;

            requestLayout();

            invalidate();
        }
    }

    private ViewPager mViewPager;

    public void setViewPager(ViewPager viewPager)
    {
        if (mViewPager != null)
        {
            mViewPager.removeOnPageChangeListener(mOnPageChangeListener);

            if (mViewPager.getAdapter() != null)
            {
                mViewPager.getAdapter().unregisterDataSetObserver(mDataSetObserver);
            }
        }

        if (viewPager == null)
        {
            setCount(0);
        }

        mViewPager = viewPager;

        if (mViewPager != null)
        {
            mViewPager.addOnPageChangeListener(mOnPageChangeListener);

            if (mViewPager.getAdapter() != null)
            {
                mViewPager.getAdapter().registerDataSetObserver(mDataSetObserver);
            }

            setCount(mViewPager.getAdapter().getCount());
        }
    }

    private int mSelectedPosition = 0;

    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
        }

        @Override
        public void onPageSelected(int position)
        {
            mSelectedPosition = position;

            invalidate();
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
        }
    };

    private DataSetObserver mDataSetObserver = new DataSetObserver()
    {
        @Override
        public void onChanged()
        {
            if (mViewPager != null && mViewPager.getAdapter() != null)
            {
                setCount(mViewPager.getAdapter().getCount());
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if (mCount == 0)
        {
            return;
        }

        final int centerX = getWidth() / 2;

        final int centerY = getHeight() / 2;

        int radius = mIndicatorSize / 2;

        if (mCount % 2 == 0) // 偶数
        {
            int pos = mCount / 2 - 1;

            // 左边
            int startX = centerX - radius - mIndicatorDividerSize / 2;

            for (int i = 0; i < mCount / 2; i++)
            {
                canvas.drawCircle(startX - i * (2 * radius + mIndicatorDividerSize), centerY, radius, mPaint);

                if (pos - i == mSelectedPosition)
                {
                    canvas.drawCircle(startX - i * (2 * radius + mIndicatorDividerSize), centerY, radius, mSelectPaint);
                }
            }

            // 右边
            startX = centerX + radius + mIndicatorDividerSize / 2;

            pos = mCount / 2;

            for (int i = 0; i < mCount / 2; i++)
            {
                canvas.drawCircle(startX + i * (2 * radius + mIndicatorDividerSize), centerY, radius, mPaint);

                if (pos + i == mSelectedPosition)
                {
                    canvas.drawCircle(startX + i * (2 * radius + mIndicatorDividerSize), centerY, radius, mSelectPaint);
                }
            }
        }
        else // 奇数
        {
            int pos = mCount / 2;

            // 中心点
            canvas.drawCircle(centerX, centerY, radius, mPaint);

            if (pos == mSelectedPosition)
            {
                canvas.drawCircle(centerX, centerY, radius, mSelectPaint);
            }

            // 左边
            for (int i = 1; i <= mCount / 2; i++)
            {
                canvas.drawCircle(centerX - i * (2 * radius + mIndicatorDividerSize), centerY, radius, mPaint);

                if (pos - i == mSelectedPosition)
                {
                    canvas.drawCircle(centerX - i * (2 * radius + mIndicatorDividerSize), centerY, radius, mSelectPaint);
                }
            }

            // 右边
            for (int i = 1; i <= mCount / 2; i++)
            {
                canvas.drawCircle(centerX + i * (2 * radius + mIndicatorDividerSize), centerY, radius, mPaint);

                if (pos + i == mSelectedPosition)
                {
                    canvas.drawCircle(centerX + i * (2 * radius + mIndicatorDividerSize), centerY, radius, mSelectPaint);
                }
            }
        }
    }
}
