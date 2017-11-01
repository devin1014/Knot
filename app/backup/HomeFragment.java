//package com.android.smartlink.ui.fragment;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.android.devin.core.ui.widget.recyclerview.CommonItemDecoration;
//import com.android.devin.core.ui.widget.recyclerview.DataBindingAdapter;
//import com.android.devin.core.ui.widget.recyclerview.DataBindingAdapter.OnItemClickListener;
//import com.android.smartlink.R;
//import com.android.smartlink.application.manager.AppManager;
//import com.android.smartlink.assist.MainRequestProvider;
//import com.android.smartlink.assist.RequestCallback;
//import com.android.smartlink.bean.Modules;
//import com.android.smartlink.ui.activity.DetailActivity;
//import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
//import com.android.smartlink.ui.model.UIModule;
//import com.android.smartlink.ui.widget.LoadingLayout;
//import com.android.smartlink.ui.widget.adapter.HomeAdapter;
//import com.android.smartlink.util.ConvertUtil;
//import com.android.smartlink.util.HttpUrl;
//
//import butterknife.BindView;
//
///**
// * User: NeuLion(wei.liu@neulion.com.com)
// * Date: 2017-10-16
// * Time: 17:55
// */
//public class HomeFragment extends BaseSmartlinkFragment implements RequestCallback<Modules>, OnRefreshListener
//{
//    @BindView(R.id.swipe_refresh_layout)
//    SwipeRefreshLayout mSwipeRefreshLayout;
//
//    @BindView(R.id.loading_layout)
//    LoadingLayout mLoadingLayout;
//
//    @BindView(R.id.recycler_view)
//    RecyclerView mRecyclerView;
//
//    private MainRequestProvider mRequestProvider;
//
//    private HomeAdapter mHomeAdapter;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
//    {
//        return inflater.inflate(R.layout.fragment_home, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
//    {
//        super.onViewCreated(view, savedInstanceState);
//
//        initComponent();
//    }
//
//    private void initComponent()
//    {
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        mRecyclerView.addItemDecoration(new CommonItemDecoration(0, getResources().getDimensionPixelSize(R.dimen.home_list_divider)));
//
//        mRecyclerView.setAdapter(mHomeAdapter = new HomeAdapter(getActivity().getLayoutInflater(), mOnHeaderClickListener));
//
//        mRequestProvider = new MainRequestProvider(getActivity(), this);
//
//        mRequestProvider.request(HttpUrl.getHomeUrl());
//
//        mLoadingLayout.showLoading();
//    }
//
//    @Override
//    public void onDestroyView()
//    {
//        mRequestProvider.destroy();
//
//        super.onDestroyView();
//    }
//
//    @Override
//    public void onResponse(Modules modules)
//    {
//        mLoadingLayout.showContent();
//
//        mSwipeRefreshLayout.setRefreshing(false);
//
//        mHomeAdapter.setData(ConvertUtil.convertModule(modules.getModules(), true));
//
//        AppManager.getInstance().setModules(modules.getModules());
//    }
//
//    @Override
//    public void onError(Throwable throwable)
//    {
//        mLoadingLayout.showMessage(getString(R.string.request_data_error));
//
//        mSwipeRefreshLayout.setRefreshing(false);
//    }
//
//    @Override
//    public void onRefresh()
//    {
//        // call request
//        mRequestProvider.request(HttpUrl.getHomeUrl());
//
//        // hide loading and show blank loading view
//        mLoadingLayout.showBlankView();
//    }
//
//    private OnItemClickListener<UIModule> mOnHeaderClickListener = new OnItemClickListener<UIModule>()
//    {
//        @Override
//        public void onItemClick(DataBindingAdapter<UIModule> adapter, View view, UIModule uiModule, int position)
//        {
//            // not status item
//            if (uiModule.getType() == UIModule.TYPE_NORMAL)
//            {
//                DetailActivity.startActivity(getActivity(), uiModule.getName(), uiModule);
//            }
//        }
//    };
//
//}
