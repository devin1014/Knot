package com.android.smartlink.assist;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 09:55
 */
public abstract class BaseRequestProvider<T>
{
    protected RequestCallback<T> mCallback;

    public BaseRequestProvider(RequestCallback<T> callback)
    {
        mCallback = callback;
    }

    public abstract void request(String url);

    public void destroy()
    {
        mCallback = null;
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
}
