package com.android.smartlink.assist;

import android.app.Activity;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Events;
import com.android.smartlink.util.ConvertUtil;
import com.android.smartlink.util.FileUtil;
import com.lzy.okgo.OkGo;

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
    protected void getFromLocal(String url)
    {
        int status = AppManager.getInstance().getDemoModeStatus();

        Events events = FileUtil.openAssets(getActivity(), "events_" + ConvertUtil.convertStatus(status) + ".json", Events.class);

        if (events != null && events.getEvents() != null)
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
        OkGo.getInstance().cancelTag(this);

        OkGo.<Events>get(url)

                .tag(this)

                .execute(new ResponseCallback());
    }

    @Override
    protected void getFromRemote(String url)
    {
        // ignore
    }

    @Override
    public void destroy()
    {
        OkGo.getInstance().cancelTag(this);

        super.destroy();
    }
}
