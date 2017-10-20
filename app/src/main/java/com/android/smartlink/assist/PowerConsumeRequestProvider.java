package com.android.smartlink.assist;

import com.android.smartlink.bean.PowerConsume;
import com.lzy.okgo.OkGo;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-19
 * Time: 11:17
 */
public class PowerConsumeRequestProvider extends BaseRequestProvider<PowerConsume>
{
    public PowerConsumeRequestProvider(RequestCallback<PowerConsume> callback)
    {
        super(callback);
    }

    @Override
    public void request(String url)
    {
        OkGo.getInstance().cancelTag(this);

        OkGo.<PowerConsume>get(url)

                .tag(this)

                .execute(new ResponseCallback()
                {
                    @Override
                    Class<PowerConsume> getConvertObjectClass()
                    {
                        return PowerConsume.class;
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
