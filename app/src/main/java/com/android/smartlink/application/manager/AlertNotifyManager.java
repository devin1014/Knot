package com.android.smartlink.application.manager;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.SystemClock;

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

    private SoundPool mSoundPool;

    private int mAlarmId;

    private int mErrorId;

    public AlertNotifyManager(Context context)
    {
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        mAlarmId = mSoundPool.load(context, R.raw.audio_alarm, 1);

        mErrorId = mSoundPool.load(context, R.raw.audio_error, 1);
    }

    public void showNotification(Context context, List<UIModule> moduleList)
    {
        boolean error = false;

        StringBuilder stringBuilder = new StringBuilder();

        String errorFormat = context.getResources().getString(R.string.notify_error);

        String alarmFormat = context.getResources().getString(R.string.notify_alarm);

        for (UIModule module : moduleList)
        {
            if (module.isError())
            {
                error = true;

                stringBuilder.append(String.format(errorFormat, module.getName())).append("\n");
            }
        }

        for (UIModule module : moduleList)
        {
            if (module.isAlarm())
            {
                stringBuilder.append(String.format(alarmFormat, module.getName())).append("\n");
            }
        }

        Notification.Builder builder = new Builder(context);

        builder.setSmallIcon(R.drawable.ic_logo)

                .setWhen(SystemClock.currentThreadTimeMillis())

                .setContentTitle(stringBuilder.toString())

                .setContentText(context.getResources().getString(R.string.notify_description))

                //.setDefaults(Notification.DEFAULT_SOUND)

                .setAutoCancel(true);

        //noinspection ConstantConditions
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(ID_NOTIFY, builder.getNotification());

        mSoundPool.play(error ? mErrorId : mAlarmId, 0.75f, 0.75f, 1, 1, 1f);
    }

    public void removeAllNotification(Context context)
    {
        //noinspection ConstantConditions
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(ID_NOTIFY);

        mSoundPool.pause(mAlarmId);

        mSoundPool.pause(mErrorId);
    }
}
