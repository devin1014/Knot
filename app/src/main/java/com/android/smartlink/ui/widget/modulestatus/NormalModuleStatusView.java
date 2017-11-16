package com.android.smartlink.ui.widget.modulestatus;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.DiffUtil.Callback;
import android.support.v7.util.DiffUtil.DiffResult;
import android.support.v7.util.ListUpdateCallback;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.smartlink.BR;
import com.android.smartlink.R;
import com.android.smartlink.bean.Modules.Module;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.util.DataBindingAdapterUtil;

import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-16
 * Time: 14:48
 */
public class NormalModuleStatusView extends LinearLayout implements OnClickListener
{
    private LayoutInflater mLayoutInflater;

    private ImageView mArrow;

    private ViewGroup mDetailGroup;

    public NormalModuleStatusView(Context context)
    {
        super(context);

        initialize();
    }

    public NormalModuleStatusView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        initialize();
    }

    public NormalModuleStatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        initialize();
    }

    private void initialize()
    {
        mLayoutInflater = LayoutInflater.from(getContext());
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

        findViewById(R.id.status_card_view).setOnClickListener(this);

        mArrow = findViewById(R.id.status_arrow);

        mDetailGroup = findViewById(R.id.status_details);
    }

    protected void onModuleChanged(final List<Module> newList, final List<Module> oldList)
    {
        //resetDetailView();

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
                return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
            {
                return oldList.get(oldItemPosition).getStatus() == newList.get(newItemPosition).getStatus();
            }
        });

        result.dispatchUpdatesTo(mListUpdateCallback);

        if (mListUpdated)
        {
            mDetailGroup.removeAllViews();

            for (Module module : newList)
            {
                View inflaterView = mLayoutInflater.inflate(R.layout.comp_home_status_detail, mDetailGroup, false);

                mDetailGroup.addView(DataBindingAdapterUtil.binding(inflaterView, BR.data, new UIModule(module)));
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        mArrow.setSelected(!mArrow.isSelected());

        resetDetailView();
    }

    private void resetDetailView()
    {
        if (mArrow.isSelected())
        {
            mDetailGroup.setVisibility(View.VISIBLE);
        }
        else
        {
            mDetailGroup.setVisibility(View.GONE);
        }
    }

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
