package com.android.smartlink.util;

import com.android.smartlink.Constants.MODULE_FLAG;
import com.android.smartlink.bean.Modules.Toggle;

/**
 * User: LIUWEI
 * Date: 2018-01-01
 * Time: 11:23
 */
public class Helper
{
    public static class ToggleHelper
    {
        public static boolean isToggleOn(Toggle toggle)
        {
            return isToggleOn(toggle.getStatus());
        }

        public static boolean isToggleOn(int status)
        {
            return status == MODULE_FLAG.STATUS_ON.value;
        }

        public static boolean isToggleEnabled(int status)
        {
            return status != MODULE_FLAG.STATUS_DISABLE.value;
        }
    }
}
