package com.android.smartlink.assist;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.android.smartlink.application.manager.AppManager;
import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;

import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: LIUWEI
 * Date: 2017-10-17
 * Time: 09:55
 */
abstract class BaseRequestProvider<T>
{
    private Activity mActivity;

    private RequestCallback<T> mCallback;

    private boolean mDestroy;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private static final ExecutorService sExecutor = Executors.newCachedThreadPool();

    BaseRequestProvider(Activity activity, RequestCallback<T> callback)
    {
        mActivity = activity;

        mCallback = callback;
    }

    public final void request(final String url)
    {
        mDestroy = false;

        if (AppManager.getInstance().isDemoMode())
        {
            sExecutor.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    getFromLocal(url);
                }
            });
        }
        else
        {
            if (!getFromOkHttp())
            {
                sExecutor.execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        getFromRemote(url);
                    }
                });
            }
            else
            {
                getFromOkHttp(url);
            }
        }
    }

    protected boolean getFromOkHttp()
    {
        return true;
    }

    protected abstract void getFromLocal(String url);

    protected abstract void getFromOkHttp(String url);

    protected abstract void getFromRemote(String url);

    public void destroy()
    {
        mActivity = null;

        mCallback = null;

        mDestroy = true;
    }

    protected Activity getActivity()
    {
        return mActivity;
    }

    protected boolean isDestroy()
    {
        return mDestroy;
    }

    @SuppressWarnings("WeakerAccess")
    protected void notifyResponse(final T t)
    {
        if (mCallback != null)
        {
            if (isUIThread())
            {
                mCallback.onResponse(t);
            }
            else if (mHandler != null)
            {
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        notifyResponse(t);
                    }
                });
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected void notifyResponse(final Throwable throwable)
    {
        if (mCallback != null)
        {
            if (isUIThread())
            {
                mCallback.onError(throwable);
            }
            else if (mHandler != null)
            {
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        notifyResponse(throwable);
                    }
                });
            }
        }
    }

    private boolean isUIThread()
    {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    abstract Class<T> getConvertObjectClass();

    @SuppressWarnings({"WeakerAccess", "ConstantConditions"})
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
