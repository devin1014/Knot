package com.android.smartlink.assist;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.application.manager.AppManager.RequestMode;
import com.android.smartlink.bean.Energy;
import com.android.smartlink.util.FileUtil;
import com.lzy.okgo.OkGo;

/**
 * User: LIUWEI
 * Date: 2017-10-19
 * Time: 11:17
 */
public abstract class EnergyRequestProvider extends BaseRequestProvider<Energy>
{
    public static EnergyRequestProvider newInstance(RequestCallback<Energy> callback)
    {
        return AppManager.getInstance().getRequestMode() == RequestMode.MODE_HTTP ?
                new HttpProvider(callback) : new LocalProvider(callback);
    }

    EnergyRequestProvider(RequestCallback<Energy> callback)
    {
        super(callback);
    }

    @Override
    Class<Energy> getConvertObjectClass()
    {
        return Energy.class;
    }

    static class LocalProvider extends EnergyRequestProvider
    {
        LocalProvider(RequestCallback<Energy> callback)
        {
            super(callback);
        }

        @Override
        public void request(String url)
        {
            Energy energy = FileUtil.openAssets(AppManager.getInstance().getApplication(), url, Energy.class);

            if (energy != null)
            {
                notifyResponse(energy);
            }
            else
            {
                notifyResponse(new EmptyDataException());
            }
        }
    }

    static class HttpProvider extends EnergyRequestProvider
    {
        public HttpProvider(RequestCallback<Energy> callback)
        {
            super(callback);
        }

        @Override
        public void request(String url)
        {
            OkGo.getInstance().cancelTag(this);

            OkGo.<Energy>get(url).tag(this).execute(new ResponseCallback());
        }

        @Override
        public void destroy()
        {
            OkGo.getInstance().cancelTag(this);

            super.destroy();
        }
    }
}
