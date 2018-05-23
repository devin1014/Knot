package com.android.smartlink.ui.widget;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by philipp on 02/06/16.
 */
public class HourAxisValueFormatter implements IAxisValueFormatter
{
    public HourAxisValueFormatter()
    {
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis)
    {
        int hour = (int) value;

        return String.valueOf(hour);
    }
}
