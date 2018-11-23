package com.android.smartlink.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.ui.activity.MyDeviceActivity;
import com.android.smartlink.ui.activity.SingleActivity;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UISetting;
import com.android.smartlink.ui.widget.adapter.SettingsAdapter;
import com.android.smartlink.util.ConvertUtil;
import com.neulion.android.diffrecycler.DiffRecyclerView;
import com.neulion.android.diffrecycler.listener.OnItemClickListener;

import butterknife.BindView;

/**
 * User: LIUWEI
 * Date: 2017-10-16
 * Time: 18:01
 */
public class SettingsFragment extends BaseSmartlinkFragment
{
    @BindView(R.id.recycler_view)
    DiffRecyclerView mRecyclerView;

    private SettingsAdapter mSettingsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
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
        mRecyclerView.setAdapter(mSettingsAdapter = new SettingsAdapter(getActivity().getLayoutInflater(), mOnItemClickListener));

        mSettingsAdapter.setData(ConvertUtil.convertSettings(getResources().getStringArray(R.array.settings),

                getResources().getStringArray(R.array.settings_image)));
    }

    private OnItemClickListener<UISetting> mOnItemClickListener = new OnItemClickListener<UISetting>()
    {
        @Override
        public void onItemClick(View view, UISetting uiSetting)
        {
            final int position = mSettingsAdapter.findItemPosition(uiSetting);

            switch (position)
            {
                case Constants.POS_SETTINGS_MY_EQUIPMENT:

                    //showDetailFragment(new MyEquipmentFragment(), setting.getName(), Constants.MODE_EDIT);
                    MyDeviceActivity.startActivity(getActivity(), uiSetting.getName());

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

                    SingleActivity.startTermsActivity(getActivity(), uiSetting.getName());

                    break;

                case Constants.POS_SETTINGS_ABOUT:

                    SingleActivity.startAboutActivity(getActivity(), uiSetting.getName());

                    break;
            }
        }
    };
}
