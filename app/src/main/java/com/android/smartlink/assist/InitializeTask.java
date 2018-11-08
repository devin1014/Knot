package com.android.smartlink.assist;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.SystemClock;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.ModuleConfiguration;
import com.google.gson.Gson;

import java.io.InputStreamReader;

/**
 * User: LIUWEI
 * Date: 2017-10-16
 * Time: 14:39
 */
public class InitializeTask extends AsyncTask<Void, Void, Boolean>
{
    private static final long MIN_DURATION = 2500L;

    private AssetManager mAssetManager;

    private InitializeTaskCallback mTaskCallback;

    public interface InitializeTaskCallback
    {
        void onInitialized(boolean success);
    }

    public InitializeTask(Context context, InitializeTaskCallback callback)
    {
        mAssetManager = context.getAssets();

        mTaskCallback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids)
    {
        final long timeStamp = SystemClock.uptimeMillis();

        Gson gson = new Gson();

        try
        {
            AppManager.getInstance().setModuleConfiguration(gson.fromJson(new InputStreamReader(mAssetManager.open("config.json")), ModuleConfiguration.class));

            if (SystemClock.uptimeMillis() - timeStamp < MIN_DURATION)
            {
                Thread.sleep(MIN_DURATION - (SystemClock.uptimeMillis() - timeStamp));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return false;
        }
        finally
        {
            mAssetManager = null;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        if (mTaskCallback != null)
        {
            mTaskCallback.onInitialized(result != null && result);
        }

        mTaskCallback = null;
    }

    public void destroy()
    {
        mTaskCallback = null;

        mAssetManager = null;

        cancel(true);
    }

}
