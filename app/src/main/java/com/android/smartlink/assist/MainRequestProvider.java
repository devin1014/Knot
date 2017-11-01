package com.android.smartlink.assist;

import android.app.Activity;

import com.android.smartlink.bean.Modules;
import com.android.smartlink.util.FileUtil;
import com.lzy.okgo.OkGo;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 18:46
 */
public class MainRequestProvider extends BaseRequestProvider<Modules>
{
    public MainRequestProvider(Activity activity, RequestCallback<Modules> callback)
    {
        super(activity, callback);
    }

    @Override
    Class<Modules> getConvertObjectClass()
    {
        return Modules.class;
    }

    @Override
    protected void requestLocal(String url)
    {
        Modules modules = FileUtil.openAssets(getActivity(), "main.json", Modules.class);

        if (modules != null)
        {
            notifyResponse(modules);
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

        OkGo.<Modules>get(url)

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
