package com.android.smartlink.ui.widget.adapter;

import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.ui.model.UIModuleImp;
import com.neulion.core.widget.recyclerview.handler.DataBindingHandler;
import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;

/**
 * User: LIUWEI
 * Date: 2017-10-22
 * Time: 13:51
 */
public class MyModulesAdapter extends BaseAdapter<UIModuleImp>
{
    private boolean mEditMode = false;

    private SparseArray<DataBindingHolder<UIModuleImp>> mSparseArray = new SparseArray<>();

    public MyModulesAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIModuleImp> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    public DataBindingHolder<UIModuleImp> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
    {
        return new Holder(inflater, parent, R.layout.list_my_module, this);
    }

    @Override
    protected int getLayout(int i)
    {
        return R.layout.list_my_module;
    }

    @Override
    public void onBindViewHolder(DataBindingHolder<UIModuleImp> holder, int position)
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
                        AppManager.getInstance().setModuleName(id, name);
                    }
                }
            }

            for (UIModuleImp module : getDataList())
            {
                module.setEditMode(editMode);
            }

            mEditMode = editMode;

            notifyDataSetChanged();
        }
    }

    private class Holder extends DataBindingHolder<UIModuleImp>
    {
        TextInputEditText editText;

        Holder(LayoutInflater inflater, ViewGroup parent, int layoutId, DataBindingHandler<UIModuleImp> handler)
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
