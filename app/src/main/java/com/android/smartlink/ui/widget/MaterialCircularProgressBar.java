package com.android.smartlink.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.android.smartlink.R;


public class MaterialCircularProgressBar extends ProgressBar
{
    private static final int DEFAULT_COLOR = 0xAAFF0000;

    private MaterialCircularProgressDrawable mProgressDrawable;

    public MaterialCircularProgressBar(Context context)
    {
        this(context, null);
    }

    public MaterialCircularProgressBar(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public MaterialCircularProgressBar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr)
    {
        mProgressDrawable = new MaterialCircularProgressDrawable();

        setIndeterminateDrawable(mProgressDrawable);

        initStyleable(context, attrs, defStyleAttr);
    }

    private void initStyleable(Context context, AttributeSet attrs, int defStyleAttr)
    {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialCircularProgressBar, defStyleAttr, 0);

        setColor(a.getColor(R.styleable.MaterialCircularProgressBar_android_color, DEFAULT_COLOR));

        a.recycle();
    }

    public void setColor(int color)
    {
        mProgressDrawable.setColor(color);
    }
}