package com.android.smartlink.assist;

import com.android.smartlink.bean.Modules;
import com.lzy.okgo.OkGo;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 18:46
 */
public class MainRequestProvider extends BaseRequestProvider<Modules>
{
    public MainRequestProvider(RequestCallback<Modules> callback)
    {
        super(callback);
    }

    @Override
    Class<Modules> getConvertObjectClass()
    {
        return Modules.class;
    }

    public void request(String url)
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
