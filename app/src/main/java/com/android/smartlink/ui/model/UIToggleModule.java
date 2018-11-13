package com.android.smartlink.ui.model;

import com.android.smartlink.ui.model.BaseModule.Module;

public interface UIToggleModule extends Module
{
    int getStatus();

    boolean toggle();
}
