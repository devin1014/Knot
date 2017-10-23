package com.android.smartlink.util;

import android.content.res.ColorStateList;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.smartlink.Constants;
import com.android.smartlink.R;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-22
 * Time: 10:19
 */
public class DataBindingAdapterUtil
{
    public static View binding(View view, int variableId, Object value)
    {
        ViewDataBinding viewDataBinding = DataBindingUtil.bind(view);

        viewDataBinding.setVariable(variableId, value);

        viewDataBinding.executePendingBindings();

        return view;
    }

    public static View binding(View view, int[] variableIds, Object[] values)
    {
        if (variableIds.length != values.length)
        {
            throw new IllegalArgumentException("variableIds array size not match values array!");
        }

        ViewDataBinding viewDataBinding = DataBindingUtil.bind(view);

        for (int i = 0; i < variableIds.length; i++)
        {
            viewDataBinding.setVariable(variableIds[i], values[i]);
        }

        viewDataBinding.executePendingBindings();

        return view;
    }

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

    @BindingAdapter({"imageRes"}) //Used for data binding. DO NOT change it
    public static void setImageRes(ImageView imageView, String drawableResName)
    {
        if (!TextUtils.isEmpty(drawableResName))
        {
            imageView.setImageResource(ViewUtil.getDrawable(imageView.getContext(), drawableResName));
        }
    }

    @BindingAdapter({"imageLevel"}) //Used for data binding. DO NOT change it
    public static void setImageLevel(ImageView imageView, int level)
    {
        imageView.setImageLevel(level);
    }

    @BindingAdapter({"backgroundRes"}) //Used for data binding. DO NOT change it
    public static void setBackgroundRes(View view, int color)
    {
        view.setBackgroundColor(color);
    }

    @BindingAdapter({"textStatusColor"})
    public static void setTextStatusColor(TextView textView, int status)
    {
        switch (status)
        {
            case Constants.STATUS_GOOD:

                textView.setTextColor(textView.getResources().getColor(R.color.module_status_good));

                break;

            case Constants.STATUS_WARNING:

                textView.setTextColor(textView.getResources().getColor(R.color.module_status_warn));

                break;

            case Constants.STATUS_ERROR:

                textView.setTextColor(textView.getResources().getColor(R.color.module_status_error));

                break;
        }
    }

    @BindingAdapter({"selected"})
    public static void setSelected(View view, boolean selected)
    {
        view.setSelected(selected);
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