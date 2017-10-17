package com.android.smartlink.ui.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;

public class MaterialCircularProgressDrawable extends Drawable implements Animatable
{
    private static final int DURATION = 1000 * 80 / 60;

    private static final int NUM_POINTS = 5;

    private static final float MAX_PROGRESS_ARC = .8F;

    private final Ring mRing = new Ring();

    private boolean mRunning;

    private int mAlpha;

    private int mColor;

    private int mRotationCount;

    private long mStartTime;

    private float mRotation;

    public MaterialCircularProgressDrawable()
    {
        mAlpha = 0xFF;

        updateColor();
    }

    public void setColor(int color)
    {
        if (mColor != color)
        {
            mColor = color;

            updateColor();
        }
    }

    private void updateColor()
    {
        final int alpha = (mColor >>> 24) * mAlpha >> 8;

        final int color = (mColor << 8 >>> 8) | (alpha << 24);

        if (color != mRing.getColor())
        {
            mRing.setColor(color);

            invalidateSelf();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // Drawable
    // -------------------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onBoundsChange(Rect bounds)
    {
        super.onBoundsChange(bounds);

        mRing.onBoundsChange(bounds);
    }

    @Override
    public void draw(Canvas canvas)
    {
        final float normalizedTime = getNormalizedTime();

        // apply animation.

        applyAnimation(normalizedTime);

        // draw.

        final int saveCount = canvas.save();

        final Rect bounds = getBounds();

        canvas.rotate(mRotation, bounds.exactCenterX(), bounds.exactCenterY());

        mRing.draw(canvas);

        canvas.restoreToCount(saveCount);

        // check states.

        if (normalizedTime >= 1F)
        {
            repeatAnimation();
        }

        // continue.

        if (mRunning)
        {
            invalidateSelf();
        }
    }

    @Override
    public void setAlpha(int alpha)
    {
        alpha += alpha >> 7;

        if (mAlpha != alpha)
        {
            mAlpha = alpha;

            updateColor();
        }
    }

    @Override
    public int getAlpha()
    {
        return mRing.getColor() >>> 24;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter)
    {
        mRing.setColorFilter(colorFilter);
    }

    @Override
    public ColorFilter getColorFilter()
    {
        return mRing.getColorFilter();
    }

    @Override
    public int getOpacity()
    {
        return PixelFormat.TRANSLUCENT;
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // Animation
    // -------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean isRunning()
    {
        return mRunning;
    }

    @Override
    public void start()
    {
        if (!mRunning)
        {
            mRunning = true;

            resetAnimation();

            invalidateSelf();
        }
    }

    @Override
    public void stop()
    {
        mRunning = false;

        mRotation = 0;

        mRing.resetState();

        invalidateSelf();
    }

    private float getNormalizedTime()
    {
        if (!mRunning)
        {
            return 0F;
        }

        final long currentTime = AnimationUtils.currentAnimationTimeMillis();

        if (mStartTime == -1L)
        {
            mStartTime = currentTime;
        }

        return Math.max(Math.min((float) (currentTime - mStartTime) / DURATION, 1F), 0F);
    }

    private void resetAnimation()
    {
        mStartTime = -1L;

        mRotationCount = 0;
    }

    private void applyAnimation(float normalizedTime)
    {
        final Ring ring = mRing;

        final float startingRotation = ring.getStartingRotation();

        final float startingTrimStart = ring.getStartingTrimStart();

        final float startingTrimEnd = ring.getStartingTrimEnd();

        ring.setRotation(startingRotation + (0.25F * normalizedTime));

        ring.setTrimStart(startingTrimStart + (MAX_PROGRESS_ARC * EndCurveInterpolator.INSTANCE.getInterpolation(normalizedTime)));

        ring.setTrimEnd(startingTrimEnd + ((MAX_PROGRESS_ARC - ring.getMinProgressArc()) * StartCurveInterpolator.INSTANCE.getInterpolation(normalizedTime)));

        mRotation = 720F * (normalizedTime + mRotationCount) / NUM_POINTS;
    }

    private void repeatAnimation()
    {
        final Ring ring = mRing;

        ring.setTrimStart(ring.getTrimEnd());

        ring.saveState();

        mStartTime = AnimationUtils.currentAnimationTimeMillis();

        mRotationCount = (mRotationCount + 1) % NUM_POINTS;
    }

    /**
     * Squishes the interpolation curve into the second half of the animation.
     */
    private static class EndCurveInterpolator extends AccelerateDecelerateInterpolator
    {
        public static final EndCurveInterpolator INSTANCE = new EndCurveInterpolator();

        @Override
        public float getInterpolation(float input)
        {
            return super.getInterpolation(Math.max(0, (input - 0.5F) * 2.0F));
        }
    }

    /**
     * Squishes the interpolation curve into the first half of the animation.
     */
    private static class StartCurveInterpolator extends AccelerateDecelerateInterpolator
    {
        public static final StartCurveInterpolator INSTANCE = new StartCurveInterpolator();

        @Override
        public float getInterpolation(float input)
        {
            return super.getInterpolation(Math.min(1, input * 2.0F));
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // Ring
    // -------------------------------------------------------------------------------------------------------------------------------------

    private static class Ring
    {
        private static final float VIEWPORT = 48F;

        private static final float RADIUS = 18F;

        private static final float STROKE_WIDTH = 4F;

        private final Paint mPaint = new Paint();

        private final RectF mBounds = new RectF();

        private float mMinProgressArc;

        private float mRotation;

        private float mTrimStart;

        private float mTrimEnd;

        private float mStartingRotation;

        private float mStartingTrimStart;

        private float mStartingTrimEnd;

        public Ring()
        {
            // setup paint.

            mPaint.setStrokeCap(Paint.Cap.SQUARE);

            mPaint.setAntiAlias(true);

            mPaint.setStyle(Style.STROKE);
        }

        public void draw(Canvas canvas)
        {
            final float start = (mTrimStart + mRotation) * 360F;

            final float end = (mTrimEnd + mRotation) * 360F;

            final float sweep = end - start;

            canvas.drawArc(mBounds, start, sweep, false, mPaint);
        }

        public void onBoundsChange(Rect bounds)
        {
            final int width = bounds.width();

            final int height = bounds.height();

            final float size = Math.min(width, height);

            if (size > 0)
            {
                final float stroke = size * STROKE_WIDTH / VIEWPORT;

                final float diameter = (size * RADIUS / VIEWPORT) * 2F;

                final float dx = (width - diameter) / 2F;

                final float dy = (height - diameter) / 2F;

                mMinProgressArc = (float) Math.toRadians(stroke / (Math.PI * diameter));

                mPaint.setStrokeWidth(stroke);

                mBounds.set(bounds.left + dx, bounds.top + dy, bounds.right - dx, bounds.bottom - dy);
            }
            else
            {
                mMinProgressArc = 0F;

                mPaint.setStrokeWidth(0F);

                mBounds.setEmpty();
            }
        }

        public void setColor(int color)
        {
            mPaint.setColor(color);
        }

        public int getColor()
        {
            return mPaint.getColor();
        }

        public void setColorFilter(ColorFilter colorFilter)
        {
            mPaint.setColorFilter(colorFilter);
        }

        public ColorFilter getColorFilter()
        {
            return mPaint.getColorFilter();
        }

        public void setRotation(float rotation)
        {
            mRotation = rotation;
        }

        public void setTrimStart(float trimStart)
        {
            mTrimStart = trimStart;
        }

        public void setTrimEnd(float trimEnd)
        {
            mTrimEnd = trimEnd;
        }

        public float getTrimEnd()
        {
            return mTrimEnd;
        }

        public float getMinProgressArc()
        {
            return mMinProgressArc;
        }

        public float getStartingRotation()
        {
            return mStartingRotation;
        }

        public float getStartingTrimStart()
        {
            return mStartingTrimStart;
        }

        public float getStartingTrimEnd()
        {
            return mStartingTrimEnd;
        }

        public void saveState()
        {
            mStartingRotation = mRotation;

            mStartingTrimStart = mTrimStart;

            mStartingTrimEnd = mTrimEnd;
        }

        public void resetState()
        {
            mStartingRotation = mRotation = 0F;

            mStartingTrimStart = mTrimStart = 0F;

            mStartingTrimEnd = mTrimEnd = 0F;
        }
    }
}