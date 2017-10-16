package com.android.smartlink.assist;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.android.smartlink.application.manager.EquipmentManager;
import com.android.smartlink.bean.Equipments;
import com.google.gson.Gson;

import java.io.IOException;
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

    public InitializeTask(Context context, InitializeTaskCallback callback)
    {
        mAssetManager = context.getAssets();

        mTaskCallback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids)
    {
        Gson gson = new Gson();

        try
        {
            Equipments equipments = gson.fromJson(new InputStreamReader(mAssetManager.open("equipment.json")), Equipments.class);

            EquipmentManager.getInstance().setEquipments(equipments);

            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        mAssetManager = null;

        mTaskCallback.onInitialized(result != null && result);
    }

    public interface InitializeTaskCallback
    {
        void onInitialized(boolean success);
    }
}
