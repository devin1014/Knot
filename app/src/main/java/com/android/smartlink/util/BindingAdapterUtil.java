package com.android.smartlink.util;

import android.content.res.ColorStateList;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BindingAdapterUtil
{
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // ImageView
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @BindingAdapter({"imageRes"}) //Used for data binding. DO NOT change it
    public static void setImageRes(ImageView imageView, int drawableResId)
    {
        if (drawableResId > 0)
        {
            imageView.setImageResource(drawableResId);
        }
    }

    @BindingAdapter({"backgroundRes"}) //Used for data binding. DO NOT change it
    public static void setBackgroundRes(View view, int color)
    {
        view.setBackgroundColor(color);
    }

    @BindingAdapter({"selected"})
    public static void setSelected(View view, boolean selected)
    {
        view.setSelected(selected);
    }

    @BindingAdapter({"lineColor"})
    public static void setLineColor(View view, int color)
    {
        view.setBackgroundColor(color);
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // TextView
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @BindingAdapter({"color"})
    public static void setColor(TextView textView, ColorStateList list)
    {
        textView.setTextColor(list);
    }
}
