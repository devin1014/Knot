package com.android.smartlink.ui.fragment.base;

import com.android.smartlink.bean.ModulesData;

/**
 * User: liuwei
 * Date: 2018-05-20
 * Time: 14:30
 */
public abstract class BaseModulesFragment extends BaseSmartlinkFragment
{
    public abstract void notifyModulesChanged(ModulesData modules);
}
