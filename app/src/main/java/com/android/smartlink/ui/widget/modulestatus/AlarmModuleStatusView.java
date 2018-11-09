package com.android.smartlink.ui.widget.modulestatus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.DiffUtil.Callback;
import android.support.v7.util.DiffUtil.DiffResult;
import android.support.v7.util.ListUpdateCallback;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.smartlink.BR;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.assist.EventsRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.bean.Events;
import com.android.smartlink.bean.Events.Event;
import com.android.smartlink.bean.Modules.Module;
import com.android.smartlink.ui.model.MonitorModuleImp;
import com.android.smartlink.ui.model.UIEvent;
import com.android.smartlink.util.AppDataBindingAdapter;
import com.android.smartlink.util.UICompat;

import java.util.Collections;
import java.util.List;

/**
 * User: LIUWEI
 * Date: 2017-11-16
 * Time: 14:48
 */
public class AlarmModuleStatusView extends LinearLayout
{
    private LayoutInflater mInflater;

    private TextView mStatus;

    private ImageView mImage;

    private ViewGroup mDetailGroup;

    private String[] mStatusArray;

    private EventsRequestProvider mEventsRequestProvider;

    public AlarmModuleStatusView(Context context)
    {
        super(context);

        initialize();
    }

    public AlarmModuleStatusView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        initialize();
    }

    public AlarmModuleStatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        initialize();
    }

    private void initialize()
    {
        mEventsRequestProvider = EventsRequestProvider.newInstance(mEventsRequestCallback);

        mInflater = LayoutInflater.from(getContext());

        mStatusArray = getResources().getStringArray(R.array.module_status_array);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

        mStatus = findViewById(R.id.status_title);

        mImage = findViewById(R.id.status_image);

        mDetailGroup = findViewById(R.id.status_details);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        mEventsRequestProvider.destroy();

        super.onDetachedFromWindow();
    }

    @SuppressLint("SetTextI18n")
    protected void onModuleChanged(final List<Module> newList, final List<Module> oldList)
    {
        mListUpdated = false;

        DiffResult result = DiffUtil.calculateDiff(new Callback()
        {
            @Override
            public int getOldListSize()
            {
                return oldList != null ? oldList.size() : 0;
            }

            @Override
            public int getNewListSize()
            {
                return newList != null ? newList.size() : 0;
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
            {
                return oldList.get(oldItemPosition).getSlaveID() == newList.get(newItemPosition).getSlaveID();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
            {
                Module oldItem = oldList.get(oldItemPosition);

                Module newItem = newList.get(newItemPosition);

                return oldItem.getStatus() == newItem.getStatus() && getPowerLoad(oldItem) == getPowerLoad(newItem);
            }
        });

        result.dispatchUpdatesTo(mListUpdateCallback);

        if (mListUpdated)
        {
            final int status = getMaxStatus(newList);

            mImage.setImageLevel(status);

            mStatus.setText(getAlarmSize(newList) + " " + mStatusArray[status]);

            mStatus.setTextColor(UICompat.getStatusColor(status));

            mEventsRequestProvider.request(AppManager.getInstance().getHttpUrl().getEventsUrl());
        }
    }

    private int getPowerLoad(Module module)
    {
        if (!TextUtils.isEmpty(module.getCurrent()))
        {
            return Math.min((int) (Float.valueOf(module.getCurrent()) / 5f * 100), 100);
        }

        return 0;
    }

    private int getMaxStatus(List<Module> list)
    {
        Module alarmModule = list.get(0);

        for (Module module : list)
        {
            if (module.getStatus() == Constants.STATUS_ERROR)
            {
                alarmModule = module;

                break;
            }

            if (MonitorModuleImp.getStatus(module) > MonitorModuleImp.getStatus(alarmModule))
            {
                alarmModule = module;
            }
        }

        return MonitorModuleImp.getStatus(alarmModule);
    }

    private int getAlarmSize(List<Module> list)
    {
        int size = 0;

        for (Module module : list)
        {
            if (module.getStatus() != Constants.STATUS_NORMAL)
            {
                size++;
            }
            else if (!TextUtils.isEmpty(module.getCurrent()))
            {
                int load = Math.min((int) (Float.valueOf(module.getCurrent()) / 5f * 100), 100);

                if (load >= Constants.POWER_LOAD_ALARM)
                {
                    size++;
                }
            }
        }

        return size;
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
                    View inflaterView = mInflater.inflate(R.layout.item_home_status_alarm, mDetailGroup, false);

                    mDetailGroup.addView(AppDataBindingAdapter.binding(inflaterView, BR.data, new UIEvent(e)));
                }
            }
        }

        @Override
        public void onError(Throwable throwable)
        {
            mDetailGroup.removeAllViews();

            mInflater.inflate(R.layout.item_home_status_alarm_error, mDetailGroup);
        }
    };

    private boolean mListUpdated = false;

    private ListUpdateCallback mListUpdateCallback = new ListUpdateCallback()
    {
        @Override
        public void onInserted(int position, int count)
        {
            mListUpdated = true;
        }

        @Override
        public void onRemoved(int position, int count)
        {
            mListUpdated = true;
        }

        @Override
        public void onMoved(int fromPosition, int toPosition)
        {
            mListUpdated = true;
        }

        @Override
        public void onChanged(int position, int count, Object payload)
        {
            mListUpdated = true;
        }
    };
}
