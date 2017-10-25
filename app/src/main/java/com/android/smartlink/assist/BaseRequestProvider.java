package com.android.smartlink.assist;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;

import java.io.InputStreamReader;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 09:55
 */
abstract class BaseRequestProvider<T>
{
    private RequestCallback<T> mCallback;

    private boolean mDestroy;

    BaseRequestProvider(RequestCallback<T> callback)
    {
        mCallback = callback;
    }

    public void request(String url)
    {
        mDestroy = false;
    }

    public void destroy()
    {
        mCallback = null;

        mDestroy = true;
    }

    protected boolean isDestroy()
    {
        return mDestroy;
    }

    protected void notifyResponse(T t)
    {
        if (mCallback != null)
        {
            mCallback.onResponse(t);
        }
    }

    protected void notifyResponse(Throwable throwable)
    {
        if (mCallback != null)
        {
            mCallback.onError(throwable);
        }
    }

    abstract Class<T> getConvertObjectClass();

    protected T convertResponse(okhttp3.Response response) throws Throwable
    {
        if (response.code() == 200 && response.body() != null)
        {
            return new Gson().fromJson(new InputStreamReader(response.body().byteStream()), getConvertObjectClass());
        }

        return null;
    }

    // ---- Callback ----------------
    class ResponseCallback extends AbsCallback<T>
    {
        @Override
        public void onSuccess(Response<T> response)
        {
            //// TODO: 2017/10/25
            if (response.body() != null)
            {
                notifyResponse(response.body());
            }
            else
            {
                notifyResponse(response.getException());
            }
        }

        @Override
        public void onError(Response<T> response)
        {
            super.onError(response);

            notifyResponse(response.getException());
        }

        @Override
        public T convertResponse(okhttp3.Response response) throws Throwable
        {
            return BaseRequestProvider.this.convertResponse(response);
        }
    }
}
