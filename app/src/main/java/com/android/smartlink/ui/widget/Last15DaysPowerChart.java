package com.android.smartlink.ui.widget;

import android.content.Context;
import android.graphics.Paint.Align;

import com.android.smartlink.util.Utils;

import org.achartengine.chart.AbstractChart;
import org.achartengine.chart.CubicLineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * User: liuwei(wei.liu@neulion.com.com)
 * Date: 2018-05-21
 * Time: 17:07
 */
public class Last15DaysPowerChart extends Last30DaysPowerChart
{
    public AbstractChart getChart(Context context, float[] data)
    {
        float[] newData = new float[data.length + 1];

        newData[0] = data[0] / 2f;

        System.arraycopy(data, 0, newData, 1, data.length / 2);

        List<float[]> xData = new ArrayList<>();

        xData.add(Utils.getPast15Days());

        List<float[]> yData = new ArrayList<>();

        yData.add(newData);

        final int lineColors = 0xFFfeb52b;

        final int labelColor = 0xFFffffff;

        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer(1);

        setRendererForTablet(renderer, new int[]{lineColors}, new PointStyle[]{PointStyle.POINT});

        setChartSettings(renderer, "", "", "", 0, 15, getMin(data), getMax(data), labelColor, labelColor);

        renderer.setShowGrid(false);
        renderer.setGridColor(labelColor, 0);
        renderer.setPanEnabled(false);
        renderer.setZoomEnabled(false, false);
        renderer.setZoomButtonsVisible(false);
        renderer.setZoomRate(1.0f);

        renderer.setXLabels(15);
        renderer.setXLabelsAlign(Align.CENTER);
        renderer.setXLabelsColor(labelColor);

        renderer.setYLabels(4);
        renderer.setYLabelsAlign(Align.RIGHT);
        renderer.setYLabelsColor(0, labelColor);

        return new CubicLineChart(buildDataSet(new String[]{""}, xData, yData), renderer, 0.3f);
    }
}
