//package com.android.smartlink.ui.widget;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Paint.Style;
//import android.graphics.Path;
//import android.graphics.PointF;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//import android.view.View;
//
///**
// * User: LIUWEI
// * Date: 2017-10-19
// * Time: 14:09
// */
//public class BezierCurveView extends View
//{
//    private Paint mPaint;
//
//    private Paint mCoordinatePaint;
//
//    private PointF[] mPoints;
//
//    private int[] mDataArray;
//
//    private Rect mRect;
//
//    public BezierCurveView(Context context)
//    {
//        this(context, null);
//    }
//
//    public BezierCurveView(Context context, AttributeSet attrs)
//    {
//        super(context, attrs);
//
//        mCoordinatePaint = new Paint();
//
//        mCoordinatePaint.setColor(Color.parseColor("#aa888888"));
//
//        mCoordinatePaint.setStrokeWidth(2f);
//
//        mCoordinatePaint.setAntiAlias(true);
//
//        mCoordinatePaint.setStyle(Style.STROKE);
//
//        mPaint = new Paint();
//
//        mPaint.setColor(Color.BLACK);
//
//        mPaint.setAntiAlias(true);
//
//        mPaint.setStyle(Style.STROKE);
//
//        mPaint.setStrokeWidth(8f);
//    }
//
//    public void setPoints(int[] data)
//    {
//        mDataArray = data;
//
//        mPoints = data != null && data.length > 0 ? new PointF[data.length] : null;
//
//        resetPoints();
//
//        invalidate();
//    }
//
//    private void resetPoints()
//    {
//        if (getWidth() != 0 && mDataArray != null)
//        {
//            int mCellWidth = getWidth() / mDataArray.length;
//
//            for (int i = 0; i < mDataArray.length; i++)
//            {
//                mPoints[i] = new PointF(mCellWidth * i, mDataArray[i]);
//            }
//        }
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldW, int oldH)
//    {
//        super.onSizeChanged(w, h, oldW, oldH);
//
//        int paddingWidth = w / 10;
//
//        int paddingHeight = h / 10;
//
//        mRect = new Rect(paddingWidth, paddingHeight, w - paddingWidth * 2, h - paddingHeight);
//
//        resetPoints();
//    }
//
//    private Path mPath = new Path();
//
//    @Override
//    protected void onDraw(Canvas canvas)
//    {
//        super.onDraw(canvas);
//
//        if (mPoints != null)
//        {
//            mPath.reset();
//
//            for (int i = 0; i < mPoints.length; i = i + 2)
//            {
//                PointF startPoint = mPoints[i];
//
//                mPath.moveTo(startPoint.x, startPoint.y);
//
//                if (i < mDataArray.length - 2)
//                {
//                    PointF ctrlPoint = mPoints[i + 1];
//
//                    PointF endPoint = mPoints[i + 2];
//
//                    mPath.quadTo(ctrlPoint.x, ctrlPoint.y, endPoint.x, endPoint.y);
//
//                    canvas.drawPath(mPath, mPaint);
//                }
//            }
//        }
//    }
//
//    private void drawCoordinate(Canvas canvas)
//    {
//        canvas.drawLine(mRect.left, mRect.bottom, mRect.right, mRect.bottom, mCoordinatePaint);
//
//        canvas.drawLine(mRect.left, mRect.bottom, mRect.left, mRect.top, mCoordinatePaint);
//    }
//}
