package com.android.smartlink.application.manager;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.content.Context;
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

    private static int sNotifyIndex = 0;

    public static void showNotification(Context context, List<UIModule> moduleList)
    {
        final int index = sNotifyIndex;

        sNotifyIndex++;

        if (index % 5 != 0)
        {
            return;
        }
        else
        {
            sNotifyIndex = 1;
        }

        StringBuilder stringBuilder = new StringBuilder();

        String errorFormat = context.getResources().getString(R.string.notify_error);

        String alarmFormat = context.getResources().getString(R.string.notify_alarm);

        for (UIModule module : moduleList)
        {
            if (module.isError())
            {
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

                .setDefaults(Notification.DEFAULT_SOUND)

                .setAutoCancel(true);

        //noinspection ConstantConditions
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(ID_NOTIFY, builder.getNotification());
    }

    public static void removeAllNotification(Context context)
    {
        sNotifyIndex = 0;

        //noinspection ConstantConditions
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(ID_NOTIFY);
    }
}
