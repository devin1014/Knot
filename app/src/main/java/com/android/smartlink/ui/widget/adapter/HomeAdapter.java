package com.android.smartlink.ui.widget.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.devin.core.ui.widget.recyclerview.DataBindingAdapter;
import com.android.devin.core.ui.widget.recyclerview.DataBindingHandler;
import com.android.devin.core.ui.widget.recyclerview.DataBindingHolder;
import com.android.smartlink.BR;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.util.DataBindingAdapterUtil;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-18
 * Time: 15:56
 */
public class HomeAdapter extends BaseAdapter<UIModule>
{
    public HomeAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIModule> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    public DataBindingHolder<UIModule> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
    {
        if (viewType == UIModule.TYPE_STATUS)
        {
            return new HeaderHolder(inflater, parent, R.layout.list_item_home_status, this);
        }

        return new DataBindingHolder<>(inflater, parent, R.layout.list_item_home_event, this);
    }

    @Override
    protected void onItemClick(DataBindingAdapter<UIModule> adapter, View view, UIModule uiModule, int position)
    {
        super.onItemClick(adapter, view, uiModule, position);
    }

    @Override
    public int getItemViewType(int position)
    {
        return getItem(position).getType();
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Holder
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    class HeaderHolder extends DataBindingHolder<UIModule>
    {
        TextView statusName;

        ImageView statusImg;

        ImageView arrowImg;

        ViewGroup statusGroup;

        @SuppressLint("SetTextI18n")
        HeaderHolder(LayoutInflater inflater, ViewGroup parent, int layoutId, DataBindingHandler<UIModule> handler)
        {
            super(inflater, parent, layoutId, handler);

            statusName = (TextView) itemView.findViewById(R.id.status_title);

            statusImg = (ImageView) itemView.findViewById(R.id.status_image);

            arrowImg = (ImageView) itemView.findViewById(R.id.arrow_image);

            statusGroup = (ViewGroup) itemView.findViewById(R.id.status_details);

            statusGroup.setVisibility(View.GONE);

            if (getDataList() != null)
            {
                for (UIModule module : getDataList())
                {
                    if (module.isItem())
                    {
                        View inflaterView = inflater.inflate(R.layout.comp_home_status_detail, statusGroup, false);

                        statusGroup.addView(DataBindingAdapterUtil.binding(inflaterView, BR.data, module));
                    }
                }
            }
        }

        @Override
        public void onViewDataBinding(int[] variableId, UIModule uiModule)
        {
            super.onViewDataBinding(variableId, uiModule);

            int status = 0;

            for (UIModule module : getDataList())
            {
                if (module.getSource().getStatus() > status)
                {
                    status = module.getSource().getStatus();
                }
            }

            statusImg.setImageLevel(status);

            statusName.setText(AppManager.getInstance().getModuleStatus(status));
        }

        @Override
        public void onItemClick(View view, UIModule uiModule)
        {
            super.onItemClick(view, uiModule);

            arrowImg.setSelected(!arrowImg.isSelected());

            statusGroup.setVisibility(arrowImg.isSelected() ? View.VISIBLE : View.GONE);
        }
    }
}
