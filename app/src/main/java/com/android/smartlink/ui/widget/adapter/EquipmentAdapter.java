package com.android.smartlink.ui.widget.adapter;

import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.devin.core.ui.widget.recyclerview.DataBindingHandler;
import com.android.devin.core.ui.widget.recyclerview.DataBindingHolder;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.ui.model.UIEquipment;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-22
 * Time: 13:51
 */
public class EquipmentAdapter extends BaseAdapter<UIEquipment>
{
    private boolean mEditMode = false;

    private SparseArray<DataBindingHolder<UIEquipment>> mSparseArray = new SparseArray<>();

    public EquipmentAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIEquipment> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    public DataBindingHolder<UIEquipment> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
    {
        return new Holder(inflater, parent, R.layout.list_item_my_equipment, this);
    }

    @Override
    public void onBindViewHolder(DataBindingHolder<UIEquipment> holder, int position)
    {
        super.onBindViewHolder(holder, position);

        mSparseArray.put(getItem(position).getId(), holder);
    }

    public void setEditMode(boolean editMode)
    {
        if (mEditMode != editMode)
        {
            if (mEditMode)
            {
                for (int i = 0; i < mSparseArray.size(); i++)
                {
                    int id = mSparseArray.keyAt(i);

                    String name = ((Holder) mSparseArray.get(id)).getEditText();

                    if (!TextUtils.isEmpty(name))
                    {
                        AppManager.getInstance().setEquipmentName(id, name);
                    }
                }
            }

            for (UIEquipment module : getDataList())
            {
                module.setEditMode(editMode);
            }

            mEditMode = editMode;

            notifyDataSetChanged();
        }
    }

    private class Holder extends DataBindingHolder<UIEquipment>
    {
        TextInputEditText editText;

        Holder(LayoutInflater inflater, ViewGroup parent, int layoutId, DataBindingHandler<UIEquipment> handler)
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
