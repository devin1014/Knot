package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.android.smartlink.R;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

/**
 * User: LIUWEI
 * Date: 2017-10-28
 * Time: 16:10
 */
public class TermsFragment extends BaseSmartlinkFragment
{
    @BindView(R.id.web_view)
    WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_terms, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        WebSettings settings = mWebView.getSettings();

        settings.setJavaScriptEnabled(false);

        settings.setLoadWithOverviewMode(false);

        settings.setUseWideViewPort(false);

        mWebView.loadUrl("file:///android_asset/conditions/default.html");
    }

    @Override
    public void onResume()
    {
        super.onResume();

        MobclickAgent.onPageStart("Terms");
    }

    @Override
    public void onPause()
    {
        MobclickAgent.onPageEnd("Terms");

        super.onPause();
    }
}
