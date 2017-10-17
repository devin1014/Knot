package com.android.smartlink.assist;

import com.android.smartlink.bean.Events;
import com.lzy.okgo.OkGo;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 11:04
 */
public class EventsRequestProvider extends BaseRequestProvider<Events>
{
    public EventsRequestProvider(RequestCallback<Events> callback)
    {
        super(callback);
    }

    @Override
    public void request(String url)
    {
        OkGo.getInstance().cancelTag(this);

        OkGo.<Events>get(url)

                .tag(this)

                .execute(new ResponseCallback()
                {
                    @Override
                    Class<Events> getConvertObjectClass()
                    {
                        return Events.class;
                    }
                });
    }

    @Override
    public void destroy()
    {
        OkGo.getInstance().cancelTag(this);

        super.destroy();
    }
}
