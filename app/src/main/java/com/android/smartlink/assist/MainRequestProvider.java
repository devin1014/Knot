package com.android.smartlink.assist;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Modules;
import com.android.smartlink.util.FileUtil;
import com.android.smartlink.util.ModbusHelp;
import com.android.smartlink.util.Utils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;

/**
 * User: LIUWEI
 * Date: 2017-10-16
 * Time: 18:46
 */
public abstract class MainRequestProvider extends BaseScheduleRequestProvider<Modules>
{
    public static MainRequestProvider newInstance(RequestCallback<Modules> callback)
    {
        if (Utils.isDevDebugMode(AppManager.getInstance().getApplication()))
        {
            return new LocalProvider(callback);
            //return new HttpProvider(callback);
        }

        return new RemoteProvider(callback);
    }

    MainRequestProvider(RequestCallback<Modules> callback)
    {
        super(callback);
    }

    @Override
    Class<Modules> getConvertObjectClass()
    {
        return Modules.class;
    }

    static class LocalProvider extends MainRequestProvider
    {
        LocalProvider(RequestCallback<Modules> callback)
        {
            super(callback);
        }

        @Override
        public void request(String url)
        {
            Modules modules = FileUtil.openAssets(AppManager.getInstance().getApplication(), url, Modules.class);

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
        HttpProvider(RequestCallback<Modules> callback)
        {
            super(callback);
        }

        @Override
        public void request(String url)
        {
            OkGo.getInstance().cancelTag(this);

            OkGo.<Modules>get(url).tag(this).execute(new ResponseCallback());
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
        RemoteProvider(RequestCallback<Modules> callback)
        {
            super(callback);
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

                    int[] registerStart = new int[]{14200, 14240, 14280, 14320, 14360, 14440};
                    String myStr = ModbusHelp.modbusRDefaultTCP("192.168.1.100", port, 255, registerStart);
                    tStr += myStr;

                    //14360, 14400
                    registerStart = new int[]{14440, 14520};
                    myStr = ModbusHelp.modbusRDefaultTCP("192.168.1.100", port, 1, registerStart);
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
