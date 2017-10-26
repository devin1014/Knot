package com.android.smartlink;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 15:10
 */
public class Constants
{
    public static final boolean DEBUG = true;

    public static final int ID_ALL = 0;

    public static final int STATUS_GOOD = 1;

    public static final int STATUS_WARNING = 2;

    public static final int STATUS_ERROR = 3;

    public static final int POS_SETTINGS_ACCOUNT = 0;

    public static final int POS_SETTINGS_MY_EQUIPMENT = 1;

    public static final int POS_SETTINGS_FEEDBACK = 2;

    public static final int POS_SETTINGS_TERMS = 3;

    public static final int POS_SETTINGS_ABOUT = 4;

    public static final String DEFAULT_LOCATION = "shanghai";

    public static final long SIX_HOUR = 6 * 60 * 60 * 1000;

    // ---- Key Extras ----------------
    private static final String KEY_EXTRA_BASE = "com.android.smartlink.key.extra";

    private static final String KEY_PREFERENCE_BASE = "com.android.smartlink.key.share";

    public static final String KEY_EXTRA_UI_MODULE = KEY_EXTRA_BASE + "uiModule";

    public static final String KEY_EXTRA_IDS = KEY_EXTRA_BASE + "ids";

    public static final String KEY_EXTRA_TITLE = KEY_EXTRA_BASE + "title";

    // ---- Share ----------------
    public static final String KEY_SHARE_PREFERENCE_SUGGEST = KEY_PREFERENCE_BASE + "suggest";

    public static final String KEY_SHARE_PREFERENCE_LOCATION = KEY_PREFERENCE_BASE + "location";

    public static final String KEY_SHARE_PREFERENCE_LOCATION_TIME = KEY_PREFERENCE_BASE + "location.time";
}
