package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.devin.core.ui.widget.IndicatorView;
import com.android.devin.core.ui.widget.recyclerview.DataBindingHandler;
import com.android.smartlink.BR;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.util.DataBindingAdapterUtil;

import butterknife.BindView;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-23
 * Time: 11:27
 */
public class DetailFragment extends BaseSmartlinkFragment
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

        mViewPager.setAdapter(new StringPageAdapter());

        mIndicatorView.setViewPager(mViewPager);
    }

    private DataBindingHandler<UIModule> mOnItemClickListener = new DataBindingHandler<UIModule>()
    {
        @Override
        public void onItemClick(View view, UIModule module)
        {
            //// TODO: 2017/10/23
        }
    };

    private class StringPageAdapter extends PagerAdapter
    {
        private String[] mDataArray;

        private LayoutInflater mInflater;

        StringPageAdapter()
        {
            mInflater = LayoutInflater.from(getActivity());

            mDataArray = getResources().getStringArray(R.array.energy_suggest_freezer);
        }

        @Override
        public int getCount()
        {
            return mDataArray.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            TextView textView = (TextView) mInflater.inflate(R.layout.list_item_engener_suggest, container, false);

            textView.setText(mDataArray[position]);

            container.addView(textView);

            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            if (object != null)
            {
                container.removeView((View) object);
            }
        }
    }
}
