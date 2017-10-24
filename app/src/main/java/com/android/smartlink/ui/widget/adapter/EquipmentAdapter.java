package com.android.smartlink.ui.widget.adapter;

import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.devin.core.ui.widget.recyclerview.DataBindingHandler;
import com.android.devin.core.ui.widget.recyclerview.DataBindingHolder;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.ui.model.UIModule;

import java.util.HashMap;
import java.util.Map;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-22
 * Time: 13:51
 */
public class EquipmentAdapter extends BaseAdapter<UIModule>
{
    private boolean mEditMode = false;

    private Map<String, DataBindingHolder> mStringMap = new HashMap<>();

    public EquipmentAdapter(LayoutInflater layoutInflater)
    {
        super(layoutInflater, null);
    }

    @Override
    public DataBindingHolder<UIModule> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
    {
        return new Holder(inflater, parent, R.layout.list_item_my_equipment, null);
    }

    @Override
    public void onBindViewHolder(DataBindingHolder<UIModule> holder, int position)
    {
        super.onBindViewHolder(holder, position);

        mStringMap.put(String.valueOf(getItem(position).getSource().getId()), holder);
    }

    public void setEditMode(boolean editMode)
    {
        if (mEditMode != editMode)
        {
            if (mEditMode)
            {
                for (String id : mStringMap.keySet())
                {
                    String name = ((Holder) mStringMap.get(id)).getEditText();

                    if (!TextUtils.isEmpty(name))
                    {
                        AppManager.getInstance().setEquipmentName(id, name);
                    }
                }
            }

            for (UIModule module : getDataList())
            {
                module.setEditMode(editMode);
            }

            mEditMode = editMode;

            notifyDataSetChanged();
        }
    }

    private class Holder extends DataBindingHolder<UIModule>
    {
        TextInputEditText editText;

        Holder(LayoutInflater inflater, ViewGroup parent, int layoutId, DataBindingHandler<UIModule> handler)
        {
            super(inflater, parent, layoutId, handler);

            editText = findViewById(R.id.edit_text);
        }

        String getEditText()
        {
            return editText.getText().toString();
        }
    }
}
