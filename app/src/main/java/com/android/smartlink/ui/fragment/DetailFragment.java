package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.devin.core.ui.widget.IndicatorView;
import com.android.devin.core.ui.widget.recyclerview.DataBindingHandler;
import com.android.smartlink.BR;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.assist.PowerConsumeRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.bean.PowerConsume;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.ui.widget.adapter.SuggestPagerAdapter;
import com.android.smartlink.util.DataBindingAdapterUtil;
import com.android.smartlink.util.FileUtil;

import butterknife.BindView;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-23
 * Time: 11:27
 */
public class DetailFragment extends BaseSmartlinkFragment implements RequestCallback<PowerConsume>
{
    public static DetailFragment newInstance(UIModule module)
    {
        DetailFragment fragment = new DetailFragment();

        Bundle arguments = new Bundle();

        arguments.putSerializable(Constants.KEY_EXTRA_UI_MODULE, module);

        fragment.setArguments(arguments);

        return fragment;
    }

    @BindView(R.id.detail_suggest)
    ViewPager mViewPager;

    @BindView(R.id.detail_indicator)
    IndicatorView mIndicatorView;

    private PowerConsumeRequestProvider mConsumeRequestProvider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent(view);
    }

    private void initComponent(View view)
    {
        UIModule uiModule = (UIModule) getArguments().getSerializable(Constants.KEY_EXTRA_UI_MODULE);

        DataBindingAdapterUtil.binding(view, new int[]{BR.data, BR.handler}, new Object[]{uiModule, mOnItemClickListener});

        mViewPager.setAdapter(new SuggestPagerAdapter(getActivity()));

        mIndicatorView.setViewPager(mViewPager);

        mConsumeRequestProvider = new PowerConsumeRequestProvider(this);

        mConsumeRequestProvider.request("http://localhost:8080/examples/smartlink/consume.json");
    }

    @Override
    public void onDestroyView()
    {
        mConsumeRequestProvider.destroy();

        super.onDestroyView();
    }

    @Override
    public void onResponse(PowerConsume powerConsume)
    {
        //// TODO: 2017/10/23
        // show chat
    }

    @Override
    public void onError(Throwable throwable)
    {
        //FIXME,can not get feed from server ,read local file
        {
            PowerConsume consume = FileUtil.openAssets(getActivity(), "consume.json", PowerConsume.class);

            if (consume != null)
            {
            }
        }
    }

    private DataBindingHandler<UIModule> mOnItemClickListener = new DataBindingHandler<UIModule>()
    {
        @Override
        public void onItemClick(View view, UIModule module)
        {
            //// TODO: 2017/10/23
        }
    };

}
