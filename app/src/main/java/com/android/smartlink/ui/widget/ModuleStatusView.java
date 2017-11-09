package com.android.smartlink.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.smartlink.BR;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.assist.EventsRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.bean.Events;
import com.android.smartlink.bean.Events.Event;
import com.android.smartlink.bean.Modules.Module;
import com.android.smartlink.ui.model.UIEvent;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.util.DataBindingAdapterUtil;
import com.android.smartlink.util.HttpUrl;
import com.android.smartlink.util.UICompat;

import java.util.Collections;
import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-08
 * Time: 15:32
 */
public class ModuleStatusView extends LinearLayout implements OnClickListener
{
    private TextView mStatus;

    private ImageView mImage;

    private ImageView mArrow;

    private ViewGroup mDetailGroup;

    private EventsRequestProvider mEventsRequestProvider;

    private LayoutInflater mInflater;

    private String[] mStatusArray;

    public ModuleStatusView(Context context)
    {
        super(context);

        initialize();
    }

    public ModuleStatusView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        initialize();
    }

    public ModuleStatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        initialize();
    }

    @SuppressWarnings("unused")
    public ModuleStatusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);

        initialize();
    }

    private void initialize()
    {
        mEventsRequestProvider = new EventsRequestProvider((Activity) getContext(), mEventsRequestCallback);

        mInflater = LayoutInflater.from(getContext());

        mStatusArray = getResources().getStringArray(R.array.module_status_array);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

        findViewById(R.id.status_top).setOnClickListener(this);

        mStatus = (TextView) findViewById(R.id.status_title);

        mImage = (ImageView) findViewById(R.id.status_image);

        mArrow = (ImageView) findViewById(R.id.arrow_image);

        mDetailGroup = (ViewGroup) findViewById(R.id.status_details);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        if (mEventsRequestProvider != null)
        {
            mEventsRequestProvider.destroy();
        }

        super.onDetachedFromWindow();
    }

    public void setModules(List<Module> modules)
    {
        final int status = getStatus(modules);

        final boolean alarm = status != Constants.STATUS_NORMAL;

        mArrow.setSelected(alarm); // alarm show status content

        mDetailGroup.setVisibility(alarm ? View.VISIBLE : View.GONE);

        mImage.setImageLevel(status);

        mStatus.setText(mStatusArray[status]);

        mStatus.setTextColor(UICompat.getStatusColor(status));

        if (!alarm)
        {
            mDetailGroup.removeAllViews();

            for (Module module : modules)
            {
                View inflaterView = mInflater.inflate(R.layout.comp_home_status_detail, mDetailGroup, false);

                mDetailGroup.addView(DataBindingAdapterUtil.binding(inflaterView, BR.data, new UIModule(module)));
            }
        }
        else
        {
            mEventsRequestProvider.request(HttpUrl.getEventsUrl());
        }
    }

    private int getStatus(List<Module> list)
    {
        Module alarmModule = list.get(0);

        for (Module module : list)
        {
            if (module.getStatus() == Constants.STATUS_ERROR)
            {
                alarmModule = module;

                break;
            }

            if (UIModule.getStatus(module) > UIModule.getStatus(alarmModule))
            {
                alarmModule = module;
            }
        }

        return UIModule.getStatus(alarmModule);
    }

    @Override
    public void onClick(View v)
    {
        mArrow.setSelected(!mArrow.isSelected());

        mDetailGroup.clearAnimation();

        if (mArrow.isSelected())
        {
            mDetailGroup.setVisibility(View.VISIBLE);
        }
        else
        {
            mDetailGroup.setVisibility(View.GONE);
        }
    }

    private RequestCallback<Events> mEventsRequestCallback = new RequestCallback<Events>()
    {
        @Override
        public void onResponse(Events events)
        {
            mDetailGroup.removeAllViews();

            Collections.sort(events.getEvents());

            for (Event e : events.getEvents())
            {
                if (e.getStatus() != Constants.STATUS_NORMAL)
                {
                    View inflaterView = mInflater.inflate(R.layout.comp_home_status_alarm, mDetailGroup, false);

                    mDetailGroup.addView(DataBindingAdapterUtil.binding(inflaterView, BR.data, new UIEvent(e)));
                }
            }
        }

        @Override
        public void onError(Throwable throwable)
        {
        }
    };
}
