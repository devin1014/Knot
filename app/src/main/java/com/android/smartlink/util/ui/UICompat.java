package com.android.smartlink.util.ui;

import android.content.res.Resources;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;

/**
 * User: LIUWEI
 * Date: 2017-10-28
 * Time: 13:45
 */
public class UICompat
{
    public static int getStatusColor(int status)
    {
        Resources resources = AppManager.getInstance().getApplication().getResources();

        switch (status)
        {
            case Constants.STATUS_NORMAL:

                return resources.getColor(R.color.module_status_good);

            case Constants.STATUS_WARNING:

                return resources.getColor(R.color.module_status_warn);

            case Constants.STATUS_ERROR:

                return resources.getColor(R.color.module_status_error);
        }

        return resources.getColor(R.color.module_status_none);
    }
}
