package com.android.smartlink.ui.activity;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.smartlink.R;
import com.android.smartlink.assist.PowerConsumeRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.bean.PowerConsume;
import com.android.smartlink.ui.activity.base.BaseSmartlinkActivity;
import com.android.smartlink.ui.widget.BezierCurveView;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import butterknife.BindView;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 18:44
 */
public class DetailActivity extends BaseSmartlinkActivity
{
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.bezier_view)
    BezierCurveView mBezierCurveView;

    @BindView(R.id.viewGroup)
    FrameLayout mFrameLayout;

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_detail;
    }

    @Override
    protected void onActivityCreate(@Nullable Bundle savedInstanceState)
    {
        new PowerConsumeRequestProvider(new RequestCallback<PowerConsume>()
        {
            @Override
            public void onResponse(PowerConsume powerConsume)
            {
                if (powerConsume != null)
                {
                    mBezierCurveView.setPoints(powerConsume.getData());

                    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

                    CategorySeries series = new CategorySeries("test");

                    for (int v : powerConsume.getData())
                    {
                        series.add(v);
                    }

                    dataset.addSeries(series.toXYSeries());

                    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
                    //		renderer.setChartTitle("当月开票");
                    //		// 设置标题的字体大小
                    //		renderer.setChartTitleTextSize(16);
                    renderer.setXTitle("事业部");
                    renderer.setYTitle("单位(万元)");
                    renderer.setAxesColor(Color.WHITE);
                    renderer.setLabelsColor(Color.WHITE);
                    // 设置X轴的最小数字和最大数字
                    renderer.setXAxisMin(0.5);
                    renderer.setXAxisMax(5.5);
                    // 设置Y轴的最小数字和最大数字
                    renderer.setYAxisMin(0);
                    renderer.setYAxisMax(3000);
                    renderer.addXTextLabel(1, "电网");
                    renderer.addXTextLabel(2, "通信");
                    renderer.addXTextLabel(3, "宽带");
                    renderer.addXTextLabel(4, "专网");
                    renderer.addXTextLabel(5, "轨交");
                    renderer.setZoomButtonsVisible(true);
                    // 设置渲染器允许放大缩小
                    renderer.setZoomEnabled(true);
                    // 消除锯齿
                    renderer.setAntialiasing(true);
                    // 设置背景颜色
                    renderer.setApplyBackgroundColor(true);
                    renderer.setBackgroundColor(Color.GRAY);
                    // 设置每条柱子的颜色
                    XYSeriesRenderer sr = new XYSeriesRenderer();
                    sr.setColor(Color.YELLOW);
                    renderer.addSeriesRenderer(sr);
                    // 设置每个柱子上是否显示数值
                    renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
                    // X轴的近似坐标数  (这样不显示横坐标)
                    renderer.setXLabels(0);
                    // Y轴的近似坐标数
                    renderer.setYLabels(6);
                    // 刻度线与X轴坐标文字左侧对齐
                    renderer.setXLabelsAlign(Align.LEFT);
                    // Y轴与Y轴坐标文字左对齐
                    renderer.setYLabelsAlign(Align.LEFT);
                    // 允许左右拖动,但不允许上下拖动.
                    renderer.setPanEnabled(true, false);
                    // 柱子间宽度
                    renderer.setBarSpacing(0.5f);
                    // 设置X,Y轴单位的字体大小
                    renderer.setAxisTitleTextSize(20);

                    mFrameLayout.addView(ChartFactory.getLineChartView(DetailActivity.this, dataset, renderer));
                }
            }

            @Override
            public void onError(Throwable throwable)
            {
                if (throwable != null)
                {
                }
            }
        }).request("http://localhost:8080/examples/smartlink/consume.json");
    }

}
