package com.android.smartlink.bean;

import com.android.smartlink.ui.model.BaseModule.ModuleParser;

public interface IModuleAddress
{
    int MONITOR_CHANNEL = 0;

    int MONITOR_SLAVE_ID_0 = 150;
    int MONITOR_SLAVE_ID_1 = 151;
    int MONITOR_SLAVE_ID_2 = 152;
    int MONITOR_SLAVE_ID_3 = 153;
    int MONITOR_SLAVE_ID_4 = 154;
    int MONITOR_SLAVE_ID_5 = 155;
    int MONITOR_SLAVE_ID_6 = 156;
    int MONITOR_SLAVE_ID_7 = 157;
    int MONITOR_SLAVE_ID_8 = 158;
    int MONITOR_SLAVE_ID_9 = 159;

    int TOGGLE_SLAVE_ID_255 = 255;

    int TOGGLE_255_CHANNEL_0 = 14200;
    int TOGGLE_255_CHANNEL_1 = 14240;
    int TOGGLE_255_CHANNEL_2 = 14280;
    int TOGGLE_255_CHANNEL_3 = 14320;
    int TOGGLE_255_CHANNEL_4 = 14360;
    int TOGGLE_255_CHANNEL_5 = 14400;
    int TOGGLE_255_CHANNEL_6 = 14440;
    int TOGGLE_255_CHANNEL_7 = 14480;
    int TOGGLE_255_CHANNEL_8 = 14520;
    int TOGGLE_255_CHANNEL_9 = 14560;

    int TOGGLE_SLAVE_ID_1 = 1;
    int TOGGLE_1_CHANNEL_1 = 14440;
    int TOGGLE_1_CHANNEL_2 = 14520;

    class ModuleAddress
    {
        public static final int[] MODULE_0 = new int[]{MONITOR_SLAVE_ID_0, MONITOR_CHANNEL};
        public static final int[] MODULE_1 = new int[]{MONITOR_SLAVE_ID_1, MONITOR_CHANNEL};
        public static final int[] MODULE_2 = new int[]{MONITOR_SLAVE_ID_2, MONITOR_CHANNEL};
        public static final int[] MODULE_3 = new int[]{MONITOR_SLAVE_ID_3, MONITOR_CHANNEL};
        public static final int[] MODULE_4 = new int[]{MONITOR_SLAVE_ID_4, MONITOR_CHANNEL};
        public static final int[] MODULE_5 = new int[]{MONITOR_SLAVE_ID_5, MONITOR_CHANNEL};
        public static final int[] MODULE_6 = new int[]{MONITOR_SLAVE_ID_6, MONITOR_CHANNEL};
        public static final int[] MODULE_7 = new int[]{MONITOR_SLAVE_ID_7, MONITOR_CHANNEL};
        public static final int[] MODULE_8 = new int[]{MONITOR_SLAVE_ID_8, MONITOR_CHANNEL};
        public static final int[] MODULE_9 = new int[]{MONITOR_SLAVE_ID_9, MONITOR_CHANNEL};

        public static final int[] TOGGLE_255_0 = new int[]{TOGGLE_SLAVE_ID_255, TOGGLE_255_CHANNEL_0};
        public static final int[] TOGGLE_255_1 = new int[]{TOGGLE_SLAVE_ID_255, TOGGLE_255_CHANNEL_1};
        public static final int[] TOGGLE_255_2 = new int[]{TOGGLE_SLAVE_ID_255, TOGGLE_255_CHANNEL_2};
        public static final int[] TOGGLE_255_3 = new int[]{TOGGLE_SLAVE_ID_255, TOGGLE_255_CHANNEL_3};
        public static final int[] TOGGLE_255_4 = new int[]{TOGGLE_SLAVE_ID_255, TOGGLE_255_CHANNEL_4};
        public static final int[] TOGGLE_255_5 = new int[]{TOGGLE_SLAVE_ID_255, TOGGLE_255_CHANNEL_5};
        public static final int[] TOGGLE_255_6 = new int[]{TOGGLE_SLAVE_ID_255, TOGGLE_255_CHANNEL_6};
        public static final int[] TOGGLE_255_7 = new int[]{TOGGLE_SLAVE_ID_255, TOGGLE_255_CHANNEL_7};
        public static final int[] TOGGLE_255_8 = new int[]{TOGGLE_SLAVE_ID_255, TOGGLE_255_CHANNEL_8};
        public static final int[] TOGGLE_255_9 = new int[]{TOGGLE_SLAVE_ID_255, TOGGLE_255_CHANNEL_9};

        public static final int[] TOGGLE_1_0 = new int[]{TOGGLE_SLAVE_ID_1, TOGGLE_1_CHANNEL_1};
        public static final int[] TOGGLE_1_1 = new int[]{TOGGLE_SLAVE_ID_1, TOGGLE_1_CHANNEL_2};

        public static int parse(int[] array)
        {
            return ModuleParser.generateId(array[0], array[1]);
        }
    }
}
