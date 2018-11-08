package com.android.smartlink.assist;

import android.app.Activity;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Events;
import com.android.smartlink.util.ConvertUtil;
import com.android.smartlink.util.FileUtil;
import com.lzy.okgo.OkGo;

/**
 * User: LIUWEI
 * Date: 2017-10-17
 * Time: 11:04
 */
public abstract class EventsRequestProvider extends BaseScheduleRequestProvider<Events>
{
    public static EventsRequestProvider newInstance(Activity activity, RequestCallback<Events> callback)
    {
        return new LocalProvider(activity, callback);
    }

    public EventsRequestProvider(Activity activity, RequestCallback<Events> callback)
    {
        super(activity, callback);
    }

    @Override
    Class<Events> getConvertObjectClass()
    {
        return Events.class;
    }

    static class LocalProvider extends EventsRequestProvider
    {
        LocalProvider(Activity activity, RequestCallback<Events> callback)
        {
            super(activity, callback);
        }

        @Override
        public void request(String url)
        {
            int status = AppManager.getInstance().getDemoModeStatus();

            Events events = FileUtil.openAssets(getActivity(), "data/events_" + ConvertUtil.convertStatus(status) + ".json", Events.class);

            if (events != null && events.getEvents() != null)
            {
                notifyResponse(events);
            }
            else
            {
                notifyResponse(new EmptyDataException());
            }
        }
    }

    static class HttpProvider extends EventsRequestProvider
    {
        HttpProvider(Activity activity, RequestCallback<Events> callback)
        {
            super(activity, callback);
        }

        @Override
        public void request(String url)
        {
            OkGo.getInstance().cancelTag(this);

            OkGo.<Events>get(url)

                    .tag(this)

                    .execute(new ResponseCallback());
        }

        @Override
        public void destroy()
        {
            OkGo.getInstance().cancelTag(this);

            super.destroy();
        }
    }
}
