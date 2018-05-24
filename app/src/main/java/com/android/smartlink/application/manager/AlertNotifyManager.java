package com.android.smartlink.application.manager;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.SystemClock;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIModule;

import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-12-12
 * Time: 17:39
 */
public class AlertNotifyManager
{
    private static final int ID_NOTIFY = 100;

    private NotificationManager mNotificationManager;

    private SoundPool mSoundPool;

    private int mAlarmId;

    private int mErrorId;

    private long mNotificationTime;

    private int mWarningLevel = Constants.STATUS_NORMAL;

    public AlertNotifyManager(Context context)
    {
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        mAlarmId = mSoundPool.load(context, R.raw.audio_alarm, 1);

        mErrorId = mSoundPool.load(context, R.raw.audio_error, 1);

        mNotificationTime = SystemClock.currentThreadTimeMillis();

        mNotificationManager = ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
    }

    public void notifyNotification(Context context, List<UIModule> moduleList)
    {
        if (checkModules(moduleList))
        {
            warning(context, moduleList);
        }
        else
        {
            stopWarning();
        }
    }

    private boolean checkModules(List<UIModule> moduleList)
    {
        for (UIModule module : moduleList)
        {
            if (module.isError() || module.isAlarm())
            {
                return true;
            }
        }

        return false;
    }

    public void warning(Context context, List<UIModule> moduleList)
    {
        final int lastWarningLevel = mWarningLevel;

        String message = parseModule(context, moduleList);

        long deltaTime = System.currentTimeMillis() - mNotificationTime;

        if (deltaTime >= 30 * 1000 ||
                (mWarningLevel == Constants.STATUS_ERROR && lastWarningLevel != Constants.STATUS_ERROR))
        {
            Notification.Builder builder = new Builder(context);

            builder.setSmallIcon(R.drawable.ic_logo)

                    .setWhen(System.currentTimeMillis())

                    .setContentTitle(message)

                    .setContentText(context.getResources().getString(R.string.notify_description))

                    //.setDefaults(Notification.DEFAULT_SOUND)

                    .setAutoCancel(true);

            //noinspection ConstantConditions
            mNotificationManager.notify(ID_NOTIFY, builder.getNotification());

            mSoundPool.play(mWarningLevel == Constants.STATUS_ERROR ? mErrorId : mAlarmId, 0.75f, 0.75f, 1, 1, 1f);

            mNotificationTime = System.currentTimeMillis();
        }
    }

    public void stopWarning()
    {
        if (mWarningLevel != Constants.STATUS_NORMAL)
        {
            mWarningLevel = Constants.STATUS_NORMAL;
            //noinspection ConstantConditions
            mNotificationManager.cancel(ID_NOTIFY);

            mSoundPool.pause(mAlarmId);

            mSoundPool.pause(mErrorId);
        }

        mNotificationTime = 0;
    }


    private String parseModule(Context context, List<UIModule> moduleList)
    {
        StringBuilder builder = new StringBuilder();

        String errorFormat = context.getResources().getString(R.string.notify_error);

        String alarmFormat = context.getResources().getString(R.string.notify_alarm);

        for (UIModule module : moduleList)
        {
            if (module.isError())
            {
                mWarningLevel = Constants.STATUS_ERROR;

                builder.append(String.format(errorFormat, module.getName())).append("\n");
            }
        }

        for (UIModule module : moduleList)
        {
            if (module.isAlarm())
            {
                if (mWarningLevel != Constants.STATUS_ERROR)
                {
                    mWarningLevel = Constants.STATUS_WARNING;
                }

                builder.append(String.format(alarmFormat, module.getName())).append("\n");
            }
        }

        return builder.toString();
    }
}
