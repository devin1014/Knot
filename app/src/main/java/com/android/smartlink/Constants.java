package com.android.smartlink;

/**
 * User: LIUWEI
 * Date: 2017-10-17
 * Time: 15:10
 */
public class Constants
{
    public static final int ID_ALL = 0;

    public static final int STATUS_NORMAL = 0;

    public static final int STATUS_WARNING = 2;

    public static final int STATUS_ERROR = 1;

    public static final int POS_SETTINGS_ACCOUNT = 0;

    public static final int POS_SETTINGS_MY_EQUIPMENT = 1;

    public static final int POS_SETTINGS_FEEDBACK = 2;

    public static final int POS_SETTINGS_TERMS = 3;

    public static final int POS_SETTINGS_ABOUT = 4;

    public static final int POWER_LOAD_ALARM = 80;

    public static final String DEFAULT_LOCATION = "shanghai";

    public static final long ONE_DAY = 24 * 60 * 60 * 1000;

    public static final long REQUEST_SCHEDULE_INTERVAL = 5 * 1000;

    // ---- Key Extras ----------------
    private static final String KEY_EXTRA_BASE = "com.android.smartlink.key.extra.";

    private static final String KEY_PREFERENCE_BASE = "com.android.smartlink.key.share.";

    public static final String KEY_EXTRA_UI_MODULE = KEY_EXTRA_BASE + "uiModule";

    public static final String KEY_EXTRA_IDS = KEY_EXTRA_BASE + "ids";

    public static final String KEY_EXTRA_TITLE = KEY_EXTRA_BASE + "title";

    public static final String KEY_EXTRA_PAGE = KEY_EXTRA_BASE + "page";

    public static final String KEY_EXTRA_MODULES = KEY_EXTRA_BASE + "modules";

    public static final String KEY_EXTRA_TOGGLE_INDEX = KEY_EXTRA_BASE + "page_index";

    // ---- Share ----------------
    public static final String KEY_SHARE_PREFERENCE_SUGGEST = KEY_PREFERENCE_BASE + "suggest";

    public static final String KEY_SHARE_PREFERENCE_LOCATION = KEY_PREFERENCE_BASE + "location";

    public static final String KEY_SHARE_PREFERENCE_LOCATION_TIME = KEY_PREFERENCE_BASE + "location.time";

    public static final String KEY_SHARE_PREFERENCE_DEMO_MODE = KEY_PREFERENCE_BASE + "demo";

    public static final String KEY_SHARE_PREFERENCE_DEMO_STATUS = KEY_PREFERENCE_BASE + "status";

    public enum MODULE_FLAG
    {
        CTRL_ON(2),

        CTRL_OFF(1),

        STATUS_DISABLE(0),

        STATUS_ON(3),

        STATUS_OFF(2);

        public final int value;

        MODULE_FLAG(int value)
        {
            this.value = value;
        }
    }

    public static final int FLAG_CTRL_ON = 2;

    public static final int FLAG_CTRL_OFF = 1;

    public static final int FLAG_MODULE_STATUS_ON = 3;

    public static final int FLAG_MODULE_STATUS_OFF = 2;
}
