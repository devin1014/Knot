package com.android.smartlink.ui.model;

import android.support.annotation.ColorInt;

public interface UIModuleBar
{
    int getStatus();

    @ColorInt
    int getTextColor();

    String getTitle();

    boolean isSelected();
}
