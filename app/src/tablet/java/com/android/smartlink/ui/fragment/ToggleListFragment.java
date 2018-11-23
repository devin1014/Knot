package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.Constants;
import com.android.smartlink.Constants.MODULE_FLAG;
import com.android.smartlink.R;
import com.android.smartlink.assist.BaseExecutorService;
import com.android.smartlink.assist.eventbus.EventBusMessages.EventModuleDataChanged;
import com.android.smartlink.bean.ModulesData;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIToggleModule;
import com.android.smartlink.util.ConvertUtil;
import com.neulion.android.diffrecycler.DiffRecyclerSimpleAdapter;
import com.neulion.android.diffrecycler.DiffRecyclerView;
import com.neulion.android.diffrecycler.listener.OnItemClickListener;

import net.lucode.hackware.magicindicator.MagicIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * User: liuwei
 * Date: 2018-05-20
 * Time: 15:04
 */
public class ToggleListFragment extends BaseSmartlinkFragment
{
    @BindView(R.id.toggle_recycler_view)
    DiffRecyclerView mRecyclerView;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;

    private DiffRecyclerSimpleAdapter<UIToggleModule> mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_toggle_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        ModulesData modules = (ModulesData) getArguments().getSerializable(Constants.KEY_EXTRA_MODULES);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5, GridLayoutManager.HORIZONTAL, false));

        mRecyclerView.setItemAnimator(null);

        mAdapter = new DiffRecyclerSimpleAdapter<>(getLayoutInflater(), R.layout.adapter_toggle, mOnItemClickListener);

        mAdapter.setData(ConvertUtil.convertToggle(modules.getToggleModules()));

        mRecyclerView.setAdapter(mAdapter);

        LinearSnapHelper pagerSnapHelper = new LinearSnapHelper();

        pagerSnapHelper.attachToRecyclerView(mRecyclerView);

        //        mMagicIndicator.setNavigator(MagicIndicatorHelper.newScaleCircleNavigator(getActivity(),
        //                2,
        //                new int[]{Color.parseColor("#ccffffff"), Color.parseColor("#ffffffff")},
        //                new int[]{2, 4}));
        //
        // ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModuleDataChangedEvent(EventModuleDataChanged event)
    {
        mAdapter.setData(ConvertUtil.convertToggle(event.modulesData.getToggleModules()));
    }

    private BaseExecutorService mExecutorService = new BaseExecutorService();

    private OnItemClickListener<UIToggleModule> mOnItemClickListener = new OnItemClickListener<UIToggleModule>()
    {
        @Override
        public void onItemClick(View view, UIToggleModule toggleModule)
        {
            boolean on = toggleModule.toggle();

            int value = on ? MODULE_FLAG.CTRL_ON.value : MODULE_FLAG.CTRL_OFF.value;

            mExecutorService.execute(toggleModule.getId(), toggleModule.getSlaveID(), value);

            mAdapter.updateItem(toggleModule);
        }
    };
}
