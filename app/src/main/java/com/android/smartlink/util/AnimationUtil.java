package com.android.smartlink.util;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;

import com.android.smartlink.R;

public class AnimationUtil
{
    public static void scaling(View view)
    {
        PropertyValuesHolder scaleXProperty = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.1f, 0.9f);

        PropertyValuesHolder scaleYProperty = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.1f, 0.9f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXProperty, scaleYProperty);

        animator.setDuration(1000);

        animator.setRepeatCount(ValueAnimator.INFINITE);

        animator.setRepeatMode(ValueAnimator.REVERSE);

        animator.start();

        view.setTag(R.id.holderAnimationTag, animator);
    }

    public static void stopAnimation(View view)
    {
        if (view != null && view.getTag(R.id.holderAnimationTag) != null)
        {
            ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(R.id.holderAnimationTag);

            objectAnimator.cancel();
        }
    }
}
