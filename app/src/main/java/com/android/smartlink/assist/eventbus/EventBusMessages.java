package com.android.smartlink.assist.eventbus;

import com.android.smartlink.bean.ModulesData;

public class EventBusMessages
{
    public static class EventEditAction
    {
        public final boolean edited;

        public EventEditAction(boolean edited)
        {
            this.edited = edited;
        }
    }

    public static class EventModuleDataChanged
    {
        public final ModulesData modulesData;

        public EventModuleDataChanged(ModulesData modulesData)
        {
            this.modulesData = modulesData;
        }
    }

    public static class EventModuleGroupChanged
    {
        public final int group;

        public EventModuleGroupChanged(int group)
        {
            this.group = group;
        }
    }
}
