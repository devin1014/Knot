package com.android.smartlink.ui.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.smartlink.R;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;

import butterknife.BindView;

/**
 * User: LIUWEI
 * Date: 2017-10-28
 * Time: 16:33
 */
public class AboutFragment extends BaseSmartlinkFragment
{
    @BindView(R.id.about_app_name)
    TextView mAppName;

    @BindView(R.id.about_app_version)
    TextView mAppVersion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        PackageManager manager = getActivity().getPackageManager();

        try
        {
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);

            mAppVersion.setText(info.versionName);

            mAppName.setText(R.string.app_name);
        }
        catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
