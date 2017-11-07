package com.android.smartlink.assist;

import android.app.Activity;
import android.net.Uri;

import com.android.smartlink.bean.Energy;
import com.android.smartlink.util.FileUtil;
import com.lzy.okgo.OkGo;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
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
    protected boolean getFromLocal()
    {
        return true;
    }

    @Override
    protected void getFromLocal(String url)
    {
        Uri uri = Uri.parse(url);

        String id = uri.getQueryParameter("id");

        Energy energy = FileUtil.openAssets(getActivity(), "30DayEnergy_" + id + ".json", Energy.class);

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
        // ignore
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