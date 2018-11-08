package com.android.smartlink.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;

import com.android.smartlink.R;

public class AlertUtil
{
    public static void showLaunchFailedAlert(final Activity activity)
    {
        AlertDialog.Builder builder = new Builder(activity);

        builder.setCancelable(false);

        builder.setMessage(R.string.app_launch_failed);

        builder.setPositiveButton(R.string.ui_ok, new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();

                activity.finish();
            }
        });

        builder.show();
    }
}
