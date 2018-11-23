package com.android.smartlink.ui.model;

import com.android.smartlink.ui.model.BaseModule.Module;
import com.neulion.android.diffrecycler.diff.DataDiffCompare;

public interface UIToggleModule extends Module, DataDiffCompare<UIToggleModule>
{
    int getStatus();

    boolean toggle();
}
