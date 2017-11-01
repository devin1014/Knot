package com.android.smartlink.assist;

import android.app.Activity;

import com.android.smartlink.bean.PowerConsume;
import com.android.smartlink.util.FileUtil;
import com.lzy.okgo.OkGo;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-19
 * Time: 11:17
 */
public class PowerConsumeRequestProvider extends BaseRequestProvider<PowerConsume>
{
    public PowerConsumeRequestProvider(Activity activity, RequestCallback<PowerConsume> callback)
    {
        super(activity, callback);
    }

    @Override
    Class<PowerConsume> getConvertObjectClass()
    {
        return PowerConsume.class;
    }

    @Override
    protected void requestLocal(String url)
    {
        PowerConsume consume = FileUtil.openAssets(getActivity(), "consume.json", PowerConsume.class);

        if (consume != null)
        {
            notifyResponse(consume);
        }
        else
        {
            notifyResponse(new EmptyDataException());
        }
    }

    @Override
    protected void requestHttp(String url)
    {
        OkGo.getInstance().cancelTag(this);

        OkGo.<PowerConsume>get(url)

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
