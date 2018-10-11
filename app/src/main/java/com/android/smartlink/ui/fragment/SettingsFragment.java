package com.android.smartlink.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.ui.activity.EquipmentActivity;
import com.android.smartlink.ui.activity.SingleActivity;
import com.android.smartlink.ui.activity.WelcomeActivity;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UISetting;
import com.android.smartlink.ui.widget.adapter.SettingsAdapter;
import com.android.smartlink.util.ConvertUtil;
import com.neulion.core.widget.recyclerview.RecyclerView;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter.OnItemClickListener;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * User: LIUWEI
 * Date: 2017-10-16
 * Time: 18:01
 */
public class SettingsFragment extends BaseSmartlinkFragment
{
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.settings_demo)
    View mSettingDemo;

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
        SettingsAdapter adapter;

        mRecyclerView.setAdapter(adapter = new SettingsAdapter(getActivity().getLayoutInflater(), mOnItemClickListener));

        adapter.setData(ConvertUtil.convertSettings(getResources().getStringArray(R.array.settings),

                getResources().getStringArray(R.array.settings_image)));

        mSettingDemo.setVisibility(AppManager.getInstance().isDemoMode() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        MobclickAgent.onPageStart("Settings");
    }

    @Override
    public void onPause()
    {
        MobclickAgent.onPageEnd("Settings");

        super.onPause();
    }

    private OnItemClickListener<UISetting> mOnItemClickListener = new OnItemClickListener<UISetting>()
    {
        @Override
        public void onItemClick(DataBindingAdapter<UISetting> adapter, View view, UISetting setting, int position)
        {
            switch (position)
            {
                case Constants.POS_SETTINGS_ACCOUNT:

                    break;

                case Constants.POS_SETTINGS_MY_EQUIPMENT:

                    //showDetailFragment(new MyEquipmentFragment(), setting.getName(), Constants.MODE_EDIT);
                    EquipmentActivity.startActivity(getActivity(), setting.getName());

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

                    SingleActivity.startTermsActivity(getActivity(), setting.getName());

                    break;

                case Constants.POS_SETTINGS_ABOUT:

                    SingleActivity.startAboutActivity(getActivity(), setting.getName());

                    break;
            }
        }
    };

    @OnClick(R.id.settings_signout)
    public void signOut()
    {
        getActivity().finish();

        getActivity().startActivity(new Intent(getActivity(), WelcomeActivity.class));
    }

    @OnClick(R.id.settings_demo)
    public void setDemoInfo()
    {
        SingleActivity.startDemoSettingActivity(getActivity(), getString(R.string.settings_demo));
    }
}
