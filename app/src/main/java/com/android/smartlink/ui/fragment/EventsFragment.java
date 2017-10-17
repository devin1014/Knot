package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.smartlink.R;
import com.android.smartlink.assist.EventsRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.bean.Events;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 18:00
 */
public class EventsFragment extends BaseSmartlinkFragment implements RequestCallback<Events>
{
    private EventsRequestProvider mRequestProvider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mRequestProvider = new EventsRequestProvider(this);

        mRequestProvider.request("http://localhost:8080/examples/smartlink/events.json");
    }

    @Override
    public void onDestroyView()
    {
        mRequestProvider.destroy();

        super.onDestroyView();
    }

    @Override
    public void onResponse(Events events)
    {
        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable throwable)
    {
        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
    }
}
