package com.android.smartlink.assist;

import android.app.Activity;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Events;
import com.android.smartlink.util.ConvertUtil;
import com.android.smartlink.util.FileUtil;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 11:04
 */
public class EventsRequestProvider extends BaseScheduleRequestProvider<Events>
{
    public EventsRequestProvider(Activity activity, RequestCallback<Events> callback)
    {
        super(activity, callback);
    }

    @Override
    Class<Events> getConvertObjectClass()
    {
        return Events.class;
    }

    @Override
    protected boolean getFromLocal()
    {
        return true;
    }

    @Override
    protected void getFromLocal(String url)
    {
        int status = AppManager.getInstance().getDemoModeStatus();

        Events events = FileUtil.openAssets(getActivity(), "events_" + ConvertUtil.convertStatus(status) + ".json", Events.class);

        if (events != null)
        {
            notifyResponse(events);
        }
        else
        {
            notifyResponse(new EmptyDataException());
        }
    }

    @Override
    protected void getFromOkHttp(String url)
    {
        // ignore
    }

    @Override
    protected void getFromRemote(String url)
    {
        // ignore
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }
}
