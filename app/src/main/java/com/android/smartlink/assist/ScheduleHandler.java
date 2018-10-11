package com.android.smartlink.assist;

import android.os.Handler;
import android.os.Message;

/**
 * User: LIUWEI
 * Date: 2017-10-26
 * Time: 15:00
 */
public class ScheduleHandler extends Handler
{
    private static final int MSG_SCHEDULE = 1;

    private long mDuration = 30 * 1000;

    public void schedule(long duration)
    {
        mDuration = duration;

        sendEmptyMessageDelayed(MSG_SCHEDULE, mDuration);
    }

    public void cancel()
    {
        removeMessages(MSG_SCHEDULE);
    }

    private OnScheduleListener mOnScheduleListener;

    public void setOnScheduleListener(OnScheduleListener listener)
    {
        mOnScheduleListener = listener;
    }

    public interface OnScheduleListener
    {
        void onScheduled();
    }

    @Override
    public void handleMessage(Message msg)
    {
        removeMessages(MSG_SCHEDULE);

        if (mOnScheduleListener != null)
        {
            mOnScheduleListener.onScheduled();
        }

        sendEmptyMessageDelayed(MSG_SCHEDULE, mDuration);
    }

}
