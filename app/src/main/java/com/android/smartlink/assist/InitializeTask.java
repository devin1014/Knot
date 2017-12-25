package com.android.smartlink.assist;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.SystemClock;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Equipments;
import com.google.gson.Gson;

import java.io.InputStreamReader;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 14:39
 */
public class InitializeTask extends AsyncTask<Void, Void, Boolean>
{
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
        if (mAssetManager == null)
        {
            return false;
        }

        final long timeStamp = SystemClock.uptimeMillis();

        boolean isPhone = AppManager.getInstance().isPhone();

        Gson gson = new Gson();

        try
        {
            Equipments equipments = gson.fromJson(new InputStreamReader(mAssetManager.open(isPhone ? "equipment.json" : "equipment_tablet.json")), Equipments.class);

            AppManager.getInstance().setEquipments(equipments);
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return false;
        }

        try
        {
            if (SystemClock.uptimeMillis() - timeStamp < 1500)
            {
                Thread.sleep(1000);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
    }

    public void destroy()
    {
        mTaskCallback = null;

        mAssetManager = null;

        cancel(true);
    }

}
