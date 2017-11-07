package com.android.smartlink.assist;

import android.app.Activity;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Modules;
import com.android.smartlink.util.FileUtil;
import com.android.smartlink.util.ModbusHelp;
import com.google.gson.Gson;
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
    protected void getFromLocal(String url)
    {
        int status = AppManager.getInstance().getDemoModeStatus();

        Modules modules = FileUtil.openAssets(getActivity(), "main" + status + ".json", Modules.class);

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
    protected void getFromRemote(String url)
    {
        String serverAddress = "192.168.1.101";

        final int port = 502;

        int[] idArr = new int[]{151, 150, 152};

        String tStr = ModbusHelp.modbusRTCP(serverAddress, port, idArr);

        Modules modules = new Gson().fromJson(tStr, Modules.class);

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
    protected void getFromOkHttp(String url)
    {
        OkGo.getInstance().cancelTag(this);

        OkGo.<Modules>get(url)

                .tag(this)

                .execute(new ResponseCallback());
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

    //    @SuppressLint("StaticFieldLeak")
    //    private class RequestTask extends AsyncTask<Void, Void, Modules>
    //    {
    //        @Override
    //        protected Modules doInBackground(Void... voids)
    //        {
    //            String serverAddress = "192.168.1.101";
    //
    //            final int port = 502;
    //
    //            int[] idArr = new int[]{151, 150, 152};
    //
    //            String tStr = ModbusHelp.modbusRTCP(serverAddress, 502, idArr);
    //
    //            return new Gson().fromJson(tStr, Modules.class);
    //        }
    //
    //        @Override
    //        protected void onPostExecute(Modules result)
    //        {
    //            if (result == null || result.getModules() == null)
    //            {
    //                notifyResponse(new EmptyDataException());
    //            }
    //            else
    //            {
    //                notifyResponse(result);
    //            }
    //        }
    //    }
}
