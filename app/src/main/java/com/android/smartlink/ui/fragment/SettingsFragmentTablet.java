package com.android.smartlink.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UISetting;
import com.android.smartlink.ui.widget.adapter.SettingsAdapter;
import com.android.smartlink.util.ConvertUtil;
import com.neulion.core.widget.recyclerview.RecyclerView;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

import butterknife.BindView;

/**
 * User: liuwei
 * Date: 2018-05-16
 * Time: 15:11
 */
public class SettingsFragmentTablet extends BaseSmartlinkFragment
{
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private SettingsAdapter mSettingsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        mSettingsAdapter = new SettingsAdapter(getActivity().getLayoutInflater(), mOnItemClickListener);

        mSettingsAdapter.setData(ConvertUtil.convertSettings(getResources().getStringArray(R.array.settings),

                getResources().getStringArray(R.array.settings_image)));

        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mRecyclerView.setAdapter(mSettingsAdapter);

        // show default account
        showFragment(R.id.settings_detail_container, new AccountFragment());
    }

    private OnItemClickListener<UISetting> mOnItemClickListener = new OnItemClickListener<UISetting>()
    {
        @Override
        public void onItemClick(View view, UISetting uiSetting)
        {
            final int position = mSettingsAdapter.findItemPosition(uiSetting);

            switch (position)
            {
                case Constants.POS_SETTINGS_ACCOUNT:

                    showFragment(R.id.settings_detail_container, new AccountFragment());

                    break;

                case Constants.POS_SETTINGS_MY_EQUIPMENT:

                    //showDetailFragment(new MyEquipmentFragment(), setting.getName(), Constants.MODE_EDIT);
                    //EquipmentActivity.startActivity(getActivity(), setting.getName());
                    showFragment(R.id.settings_detail_container, new DevicesFragment());

                    break;

                case Constants.POS_SETTINGS_FEEDBACK:

                    Uri uri = Uri.parse("mailto:yin.liu@schneider-electric.com");
                    String[] email = {"yin.liu@schneider-electric.com"};
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra(Intent.EXTRA_EMAIL, email);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "App反馈"); // 主题
                    intent.putExtra(Intent.EXTRA_TEXT, "感觉您的反馈意见"); // 正文
                    startActivity(Intent.createChooser(intent, "请选择发送邮件方式"));

                    break;

                case Constants.POS_SETTINGS_TERMS:

                    showFragment(R.id.settings_detail_container, new TermsFragment());

                    break;

                case Constants.POS_SETTINGS_ABOUT:

                    showFragment(R.id.settings_detail_container, new AboutFragment());

                    break;
            }
        }
    };

    //    @OnClick(R.id.settings_signout)
    //    public void signOut()
    //    {
    //        getActivity().finish();
    //
    //        getActivity().startActivity(new Intent(getActivity(), WelcomeActivity.class));
    //    }

    private void showFragment(int containerId, Fragment fragment)
    {
        getChildFragmentManager().beginTransaction().replace(containerId, fragment).commit();
    }
}
