package com.android.smartlink.assist;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;

import com.android.smartlink.bean.Energy;
import com.android.smartlink.util.FileUtil;
import com.lzy.okgo.OkGo;

/**
 * User: LIUWEI
 * Date: 2017-10-19
 * Time: 11:17
 */
public class EnergyRequestProvider extends BaseRequestProvider<Energy>
{
    public EnergyRequestProvider(Activity activity, RequestCallback<Energy> callback)
    {
        super(activity, callback);
    }

    @Override
    Class<Energy> getConvertObjectClass()
    {
        return Energy.class;
    }

    @Override
    protected void getFromLocal(String url)
    {
        String name;

        Uri uri = Uri.parse(url);

        String id = uri.getQueryParameter("id");

        if (TextUtils.isEmpty(id))
        {
            name = "data/" + uri.getPath().substring(uri.getPath().lastIndexOf("/") + 1);
        }
        else
        {
            name = "data/30DayEnergy_" + id + ".json";
        }

        Energy energy = FileUtil.openAssets(getActivity(), name, Energy.class);

        if (energy != null)
        {
            notifyResponse(energy);
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

        OkGo.<Energy>get(url)

                .tag(this)

                .execute(new ResponseCallback());
    }

    @Override
    protected void getFromRemote(String url)
    {
        getFromLocal(url);
    }

    @Override
    protected boolean getFromOkHttp()
    {
        return false;
    }

    @Override
    public void destroy()
    {
        OkGo.getInstance().cancelTag(this);

        super.destroy();
    }
}
