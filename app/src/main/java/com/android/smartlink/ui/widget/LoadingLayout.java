package com.android.smartlink.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.smartlink.R;
import com.android.smartlink.util.ui.ViewUtil;


/**
 * it extends FrameLayout, it's first child view is content view.
 *
 * @see FrameLayout
 */
public class LoadingLayout extends FrameLayout
{
    private static final int DEFAULT_BACKGROUND_COLOR = 0x00000000;

    private View mContentView;

    private View mLoadingView;

    private View mProgressBar;

    private TextView mMessageTextView;

    private int mInflaterLayoutId = -1;

    private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;

    public LoadingLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        initStyleable(context, attrs);
    }

    private void initStyleable(Context context, AttributeSet attrs)
    {
        if (attrs == null)
        {
            return;
        }

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout, 0, 0);

        mInflaterLayoutId = a.getResourceId(R.styleable.LoadingLayout_loading_layout, -1);

        mBackgroundColor = a.getColor(R.styleable.LoadingLayout_loading_background, DEFAULT_BACKGROUND_COLOR);

        a.recycle();
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

        if (getChildCount() != 1)
        {
            throw new IllegalArgumentException("LoadingLayout must has one child view! current count=" + getChildCount());
        }

        mContentView = getChildAt(0);

        final View inflaterView = LayoutInflater.from(getContext()).inflate(mInflaterLayoutId, this, false);

        inflaterView.setBackgroundColor(mBackgroundColor);

        mProgressBar = inflaterView.findViewById(R.id.loading_progressbar);

        mMessageTextView = inflaterView.findViewById(R.id.loading_message);

        mLoadingView = inflaterView;

        addView(inflaterView);
    }

    /**
     * show loading view and hide content view
     */
    public void showLoading()
    {
        // show loading view
        ViewUtil.setVisibility(mLoadingView, true);

        mLoadingView.setBackgroundColor(mBackgroundColor);

        ViewUtil.setVisibility(mProgressBar, true);

        ViewUtil.setVisibility(mMessageTextView, false);

        // hide content view
        ViewUtil.setVisibility(mContentView, false);
    }

    public void showBlankView()
    {
        // show loading view
        ViewUtil.setVisibility(mLoadingView, true);

        mLoadingView.setBackgroundColor(mBackgroundColor);

        ViewUtil.setVisibility(mProgressBar, false);

        ViewUtil.setVisibility(mMessageTextView, false);

        // hide content view
        ViewUtil.setVisibility(mContentView, false);
    }

    /**
     * show message view and hide content view
     */
    public void showMessage(String message)
    {
        // show loading view
        ViewUtil.setVisibility(mLoadingView, true);

        mLoadingView.setBackgroundColor(mBackgroundColor);

        ViewUtil.setVisibility(mProgressBar, false);

        ViewUtil.setVisibility(mMessageTextView, true);

        mMessageTextView.setText(message);

        // hide content view
        ViewUtil.setVisibility(mContentView, false);
    }

    /**
     * show loading view and <b>not</b> hide content view
     */
    public void showFrameLoading()
    {
        // show loading view
        ViewUtil.setVisibility(mLoadingView, true);

        // clear loading background
        mLoadingView.setBackgroundColor(DEFAULT_BACKGROUND_COLOR);

        ViewUtil.setVisibility(mProgressBar, true);

        ViewUtil.setVisibility(mMessageTextView, false);

        // show content view
        ViewUtil.setVisibility(mContentView, true);

        mContentView.setEnabled(false);
    }

    /**
     * hide loading view and show content view
     */
    public void showContent()
    {
        // show content view
        ViewUtil.setVisibility(mContentView, true);

        mContentView.setEnabled(true);

        // hide loading view
        ViewUtil.setVisibility(mLoadingView, false);
    }

}
