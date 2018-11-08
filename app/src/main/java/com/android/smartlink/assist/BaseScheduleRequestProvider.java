package com.android.smartlink.assist;

import java.util.Timer;
import java.util.TimerTask;

/**
 * User: LIUWEI
 * Date: 2017-11-08
 * Time: 18:03
 */
public abstract class BaseScheduleRequestProvider<T> extends BaseRequestProvider<T>
{
    private Timer mTimer;

    BaseScheduleRequestProvider(RequestCallback<T> callback)
    {
        super(callback);
    }

    public void schedule(final String url, long delay, long interval)
    {
        if (mTimer != null)
        {
            mTimer.cancel();
        }

        mTimer = new Timer();

        mTimer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                if (!isDestroy())
                {
                    request(url);
                }
            }
        }, delay, interval);
    }

    @Override
    public void destroy()
    {
        if (mTimer != null)
        {
            mTimer.cancel();
        }

        super.destroy();
    }
}
