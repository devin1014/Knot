package com.android.smartlink.ui.widget;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by philipp on 02/06/16.
 */
public class MonthAxisValueFormatter implements IAxisValueFormatter
{
    public MonthAxisValueFormatter()
    {
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis)
    {
        return String.valueOf((int) (value + 1));
    }
}
