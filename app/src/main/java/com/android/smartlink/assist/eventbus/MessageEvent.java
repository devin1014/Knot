package com.android.smartlink.assist.eventbus;

public class MessageEvent
{
    public static class EditModuleEvent
    {
        public final boolean edited;

        public EditModuleEvent(boolean edited)
        {
            this.edited = edited;
        }
    }
}
