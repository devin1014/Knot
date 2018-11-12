package com.android.smartlink.assist;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.application.manager.AppManager.RequestMode;
import com.android.smartlink.bean.Events;
import com.android.smartlink.bean.Events.Event;
import com.android.smartlink.ui.model.BaseModule.ModuleParser;
import com.android.smartlink.util.FileUtil;
import com.lzy.okgo.OkGo;

/**
 * User: LIUWEI
 * Date: 2017-10-17
 * Time: 11:04
 */
public abstract class EventsRequestProvider extends BaseScheduleRequestProvider<Events>
{
    public static EventsRequestProvider newInstance(RequestCallback<Events> callback)
    {
        return AppManager.getInstance().getRequestMode() == RequestMode.MODE_HTTP ?
                new HttpProvider(callback) : new LocalProvider(callback);
    }

    EventsRequestProvider(RequestCallback<Events> callback)
    {
        super(callback);
    }

    @Override
    Class<Events> getConvertObjectClass()
    {
        return Events.class;
    }

    @Override
    protected Events processResult(Events events)
    {
        if (events != null && events.getEvents() != null)
        {
            for (Event e : events.getEvents())
            {
                //FIXME: should not generate id here!
                e.setId(ModuleParser.generateId(e.getSlaveID(), e.getChannel()));
            }
        }

        return events;
    }

    static class LocalProvider extends EventsRequestProvider
    {
        LocalProvider(RequestCallback<Events> callback)
        {
            super(callback);
        }

        @Override
        public void request(String url)
        {
            Events events = FileUtil.openAssets(AppManager.getInstance().getApplication(), url, Events.class);

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
        HttpProvider(RequestCallback<Events> callback)
        {
            super(callback);
        }

        @Override
        public void request(String url)
        {
            OkGo.getInstance().cancelTag(this);

            OkGo.<Events>get(url).tag(this).execute(new ResponseCallback());
        }

        @Override
        public void destroy()
        {
            OkGo.getInstance().cancelTag(this);

            super.destroy();
        }
    }
}
