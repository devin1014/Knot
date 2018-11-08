package com.android.smartlink.assist;

import android.app.Activity;
import android.text.TextUtils;

import com.android.devin.core.util.LogUtil;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Modules;
import com.android.smartlink.util.ConvertUtil;
import com.android.smartlink.util.FileUtil;
import com.android.smartlink.util.ModbusHelp;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;

/**
 * User: LIUWEI
 * Date: 2017-10-16
 * Time: 18:46
 */
public abstract class MainRequestProvider extends BaseScheduleRequestProvider<Modules>
{
    public static MainRequestProvider newInstance(Activity activity, RequestCallback<Modules> callback)
    {
        if (AppManager.getInstance().isDemoMode())
        {
            return new LocalProvider(activity, callback);
        }

        return new RemoteProvider(activity, callback);
    }

    public MainRequestProvider(Activity activity, RequestCallback<Modules> callback)
    {
        super(activity, callback);
    }

    @Override
    Class<Modules> getConvertObjectClass()
    {
        return Modules.class;
    }

    static class LocalProvider extends MainRequestProvider
    {
        LocalProvider(Activity activity, RequestCallback<Modules> callback)
        {
            super(activity, callback);
        }

        @Override
        public void request(String url)
        {
            int status = AppManager.getInstance().getDemoModeStatus();

            Modules modules = FileUtil.openAssets(getActivity(), "data/main_" + ConvertUtil.convertStatus(status) + ".json", Modules.class);

            if (modules != null)
            {
                notifyResponse(modules);
            }
            else
            {
                notifyResponse(new EmptyDataException());
            }
        }
    }

    static class HttpProvider extends MainRequestProvider
    {
        HttpProvider(Activity activity, RequestCallback<Modules> callback)
        {
            super(activity, callback);
        }

        @Override
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

    static class RemoteProvider extends MainRequestProvider
    {
        RemoteProvider(Activity activity, RequestCallback<Modules> callback)
        {
            super(activity, callback);
        }

        @Override
        public void request(String url)
        {
            getsExecutor().execute(new Runnable()
            {
                @Override
                public void run()
                {
                    String serverAddress = "192.168.1.101";

                    final int port = 502;

                    int[] idArr = new int[]{150, 152, 155, 154, 153, 151, 156}; //157备用回路

                    String tStr = ModbusHelp.modbusRTCP(serverAddress, port, idArr);
                    LogUtil.warn(this, TextUtils.isEmpty(tStr) ? "NULL" : tStr);

                    int[] registerStart = new int[]{14200, 14240, 14280, 14320, 14360, 14440};
                    String myStr = ModbusHelp.modbusRDefaultTCP("192.168.1.100", port, 255, registerStart);
                    LogUtil.warn(this, TextUtils.isEmpty(tStr) ? "NULL" : myStr);
                    tStr += myStr;

                    //14360, 14400
                    registerStart = new int[]{14440, 14520};
                    myStr = ModbusHelp.modbusRDefaultTCP("192.168.1.100", port, 1, registerStart);
                    LogUtil.warn(this, TextUtils.isEmpty(tStr) ? "NULL" : myStr);
                    tStr = tStr + "," + myStr + "}";

                    //        LogUtil.warn(this, TextUtils.isEmpty(tStr) ? "NULL" : tStr);

                    Modules modules = null;
                    try
                    {
                        modules = new Gson().fromJson(tStr, Modules.class);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    if (modules != null)
                    {
                        notifyResponse(modules);
                    }
                    else
                    {
                        notifyResponse(new EmptyDataException());
                    }
                }
            });
        }
    }
}
