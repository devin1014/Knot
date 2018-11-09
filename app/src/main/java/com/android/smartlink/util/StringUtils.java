package com.android.smartlink.util;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

/**
 * User: LIUWEI
 * Date: 2017-11-02
 * Time: 15:30
 */
public class StringUtils
{
    public static void setSpannable(SpannableStringBuilder spannableString, int start, int end, Object... objects)
    {
        if (objects != null)
        {
            for (Object obj : objects)
            {
                spannableString.setSpan(obj, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
    }

    public static boolean isEmpty(int... params)
    {
        return params == null || params.length == 0;
    }
}
