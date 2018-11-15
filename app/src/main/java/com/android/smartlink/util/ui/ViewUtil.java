package com.android.smartlink.util.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


public class ViewUtil
{
    public static int getDrawable(Context context, String name)
    {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public static void setVisibility(View view, boolean visible)
    {
        if (view != null)
        {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    private static final String EMPTY = "";

    public static void setText(TextView textView, CharSequence text)
    {
        if (textView != null)
        {
            textView.setText(TextUtils.isEmpty(text) ? EMPTY : text);
        }
    }

    public static void setText(View root, int textViewId, CharSequence text)
    {
        if (root != null && textViewId > 0)
        {
            View view = root.findViewById(textViewId);

            if (view instanceof TextView)
            {
                ((TextView) view).setText(text);
            }
        }
    }
}
