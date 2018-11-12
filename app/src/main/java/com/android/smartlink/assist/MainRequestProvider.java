package com.android.smartlink.assist;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.application.manager.AppManager.RequestMode;
import com.android.smartlink.bean.ModulesData;
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
public abstract class MainRequestProvider extends BaseScheduleRequestProvider<ModulesData>
{
    public static MainRequestProvider newInstance(RequestCallback<ModulesData> callback)
    {
        if (Utils.isDevDebugMode(AppManager.getInstance().getApplication()))
        {
            return AppManager.getInstance().getRequestMode() == RequestMode.MODE_HTTP ?
                    new HttpProvider(callback) : new LocalProvider(callback);
        }

        return new RemoteProvider(callback);
    }

    MainRequestProvider(RequestCallback<ModulesData> callback)
    {
        super(callback);
    }

    @Override
    Class<ModulesData> getConvertObjectClass()
    {
        return ModulesData.class;
    }

    static class LocalProvider extends MainRequestProvider
    {
        LocalProvider(RequestCallback<ModulesData> callback)
        {
            super(callback);
        }

        @Override
        public void request(String url)
        {
            ModulesData modules = FileUtil.openAssets(AppManager.getInstance().getApplication(), url, ModulesData.class);

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
        HttpProvider(RequestCallback<ModulesData> callback)
        {
            super(callback);
        }

        @Override
        public void request(String url)
        {
            OkGo.getInstance().cancelTag(this);

            OkGo.<ModulesData>get(url).tag(this).execute(new ResponseCallback());
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
        RemoteProvider(RequestCallback<ModulesData> callback)
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

                    ModulesData modules = null;
                    try
                    {
                        modules = new Gson().fromJson(tStr, ModulesData.class);
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
