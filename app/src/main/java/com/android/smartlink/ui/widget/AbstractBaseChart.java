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

import android.graphics.Color;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

/**
 * An abstract class for the demo charts to extend. It contains some methods for
 * building dataSets and renderer.
 */
abstract class AbstractBaseChart
{
    /**
     * Builds an XY multiple dataSet using the provided values.
     *
     * @param titles  the series titles
     * @param xValues the values for the X axis
     * @param yValues the values for the Y axis
     * @return the XY multiple dataSet
     */
    XYMultipleSeriesDataset buildDataSet(String[] titles, List<float[]> xValues, List<float[]> yValues)
    {
        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();

        for (int i = 0; i < titles.length; i++)
        {
            XYSeries series = new XYSeries(titles[i], 0);

            float[] xV = xValues.get(i);

            float[] yV = yValues.get(i);

            int seriesLength = xV.length;

            for (int k = 0; k < seriesLength; k++)
            {
                series.add(xV[k], yV[k]);
            }

            dataSet.addSeries(series);
        }

        return dataSet;
    }

    void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles)
    {
        setRenderer(renderer, colors, styles, 20, 5f, new int[]{20, 30, 15, 20}, 4f);
    }

    void setRendererForTablet(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles)
    {
        setRenderer(renderer, colors, styles, 15, 3f, new int[]{20, 20, 0, 20}, 3f);
    }

    void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles, int textSize, float pointSize, int[] margings, float lineWidth)
    {
        renderer.setAxisTitleTextSize(textSize);

        renderer.setChartTitleTextSize(textSize);

        renderer.setLabelsTextSize(textSize);

        renderer.setLegendTextSize(textSize);

        renderer.setPointSize(pointSize);

        renderer.setMargins(margings);

        renderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));

        for (int i = 0; i < colors.length; i++)
        {
            XYSeriesRenderer r = new XYSeriesRenderer();

            r.setLineWidth(lineWidth);

            r.setColor(colors[i]);

            r.setPointStyle(styles[i]);

            renderer.addSeriesRenderer(r);
        }
    }

    /**
     * Sets a few of the series renderer settings.
     *
     * @param renderer    the renderer to set the properties to
     * @param title       the chart title
     * @param xTitle      the title for the X axis
     * @param yTitle      the title for the Y axis
     * @param xMin        the minimum value on the X axis
     * @param xMax        the maximum value on the X axis
     * @param yMin        the minimum value on the Y axis
     * @param yMax        the maximum value on the Y axis
     * @param axesColor   the axes color
     * @param labelsColor the labels color
     */
    void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
                          String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor)
    {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }

}
