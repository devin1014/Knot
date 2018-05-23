package com.android.smartlink.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

/**
 * User: liuwei(wei.liu@neulion.com.com)
 * Date: 2018-05-22
 * Time: 17:56
 */
public class ModuleBarChart extends BarChart
{
    private int mXAxisMaxSize = 24;

    public ModuleBarChart(Context context)
    {
        super(context);

        initialize();
    }

    public ModuleBarChart(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        initialize();
    }

    public ModuleBarChart(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        initialize();
    }

    private void initialize()
    {
        setDrawBarShadow(false);
        setDrawValueAboveBar(false);
        getDescription().setEnabled(false);
        //setMaxVisibleValueCount(24);
        // scaling can now only be done on x- and y-axis separately
        setPinchZoom(false);
        setDoubleTapToZoomEnabled(false);
        setDrawGridBackground(false);
        // setDrawYLabels(false);

        IAxisValueFormatter xAxisFormatter = new HourAxisValueFormatter();
        XAxis xAxis = getXAxis();
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(2f); // only intervals of 1 day
        xAxis.setLabelCount(mXAxisMaxSize);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);

        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = getAxisLeft();
        leftAxis.setLabelCount(4, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(4, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setAxisLineColor(Color.WHITE);
        rightAxis.setTextColor(Color.WHITE);

        Legend l = getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        XYMarkerView mv = new XYMarkerView(getContext(), xAxisFormatter);
        mv.setChartView(this); // For bounds control
        setMarker(mv); // Set the marker to the chart
    }

    public void setData(float[] data)
    {
        ArrayList<BarEntry> yValues = new ArrayList<>();

        for (int i = 0; i < mXAxisMaxSize; i++)
        {
            yValues.add(new BarEntry(i, data[i]));
        }

        BarDataSet dataSet;

        if (getData() != null && getData().getDataSetCount() > 0)
        {
            dataSet = (BarDataSet) getData().getDataSetByIndex(0);
            dataSet.setValues(yValues);
            getData().notifyDataChanged();
            notifyDataSetChanged();
        }
        else
        {
            dataSet = new BarDataSet(yValues, null);
            dataSet.setDrawIcons(false);
            dataSet.setDrawValues(false);
            dataSet.setColors(Color.GREEN);

            ArrayList<IBarDataSet> list = new ArrayList<>();
            list.add(dataSet);

            BarData barData = new BarData(list);
            barData.setValueTextSize(10f);
            barData.setValueTextColor(Color.WHITE);
            barData.setBarWidth(0.8f);

            setData(barData);
        }
    }

}
