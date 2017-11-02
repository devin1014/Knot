/*
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.smartlink.ui.widget;

import android.content.Context;
import android.graphics.Paint.Align;

import com.android.smartlink.R;
import com.android.smartlink.util.Utils;

import org.achartengine.chart.AbstractChart;
import org.achartengine.chart.CubicLineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Multiple temperature demo chart.
 */
public class Last30DaysPowerChart extends AbstractBaseChart
{
    public AbstractChart getChart(Context context, float[] data)
    {
        float[] newData = new float[data.length + 1];

        newData[0] = data[0] / 2f;

        System.arraycopy(data, 0, newData, 1, data.length);

        List<float[]> xData = new ArrayList<>();

        xData.add(Utils.getPast30Days());

        List<float[]> yData = new ArrayList<>();

        yData.add(newData);

        int[] lineColors = new int[]{0xFF019944};

        int labelColor = 0xaa019944;

        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer(1);

        setRenderer(renderer, lineColors, new PointStyle[]{PointStyle.POINT});

        setChartSettings(renderer, "", "", "", 0, 30, getMin(data), getMax(data), labelColor, labelColor);

        renderer.setShowGrid(true);
        renderer.setGridColor(labelColor, 0);
        renderer.setPanEnabled(false);
        renderer.setZoomEnabled(false, false);
        renderer.setZoomButtonsVisible(false);
        renderer.setZoomRate(1.0f);

        renderer.setXLabels(30);
        renderer.setXLabelsAlign(Align.RIGHT);
        renderer.setXLabelsColor(lineColors[0]);

        renderer.setYLabels(3);
        renderer.setYLabelsAlign(Align.RIGHT);
        renderer.setYLabelsColor(0, lineColors[0]);

        return new CubicLineChart(buildDataSet(new String[]{context.getResources().getString(R.string.power_last_30_days)}, xData, yData), renderer, 0.3f);
    }

    private float getMin(float[] data)
    {
        float min = Float.MAX_VALUE;

        for (float f : data)
        {
            if (f < min)
            {
                min = f;
            }
        }

        return Math.max(min - 0.5f, 0.1f);
    }

    private float getMax(float[] data)
    {
        float max = Float.MIN_VALUE;

        for (float f : data)
        {
            if (f > max)
            {
                max = f;
            }
        }

        return max + 1;
    }
}
