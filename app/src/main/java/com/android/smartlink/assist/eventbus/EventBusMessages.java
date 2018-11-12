package com.android.smartlink.assist.eventbus;

import com.android.smartlink.bean.ModulesData;

public class EventBusMessages
{
    public static class EditModuleEvent
    {
        public final boolean edited;

        public EditModuleEvent(boolean edited)
        {
            this.edited = edited;
        }
    }

    public static class ModuleDataChangedEvent
    {
        public final ModulesData modulesData;

        public ModuleDataChangedEvent(ModulesData modulesData)
        {
            this.modulesData = modulesData;
        }
    }
}
