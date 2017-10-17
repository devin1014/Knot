package com.android.smartlink.assist;

import com.android.smartlink.bean.Modules;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;

import java.io.InputStreamReader;

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

    public void request(String url)
    {
        OkGo.getInstance().cancelTag(this);

        OkGo.<Modules>get(url)

                .tag(this)

                .execute(new AbsCallback<Modules>()
                {
                    @Override
                    public void onSuccess(Response<Modules> response)
                    {
                        if (response != null)
                        {
                            if (response.body() != null)
                            {
                                notifyResponse(response.body());
                            }
                            else
                            {
                                notifyResponse(response.getException());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<Modules> response)
                    {
                        super.onError(response);

                        notifyResponse(response.getException());
                    }

                    @Override
                    public Modules convertResponse(okhttp3.Response response) throws Throwable
                    {
                        if (response != null)
                        {
                            if (response.code() == 200 && response.body() != null)
                            {
                                return new Gson().fromJson(new InputStreamReader(response.body().byteStream()), Modules.class);
                            }
                        }

                        return null;
                    }
                });
    }

    @Override
    public void destroy()
    {
        OkGo.getInstance().cancelTag(this);

        super.destroy();
    }
}
