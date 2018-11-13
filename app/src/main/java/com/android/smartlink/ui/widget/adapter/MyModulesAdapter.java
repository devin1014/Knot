package com.android.smartlink.ui.widget.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;

import com.android.smartlink.BR;
import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIDeviceImp;
import com.neulion.core.widget.recyclerview.adapter.DiffDataBindingAdapter;
import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

/**
 * User: LIUWEI
 * Date: 2017-10-22
 * Time: 13:51
 */
public class MyModulesAdapter extends DiffDataBindingAdapter<UIDeviceImp>
{
    //private boolean mEditMode = false;

    private SparseArray<DataBindingHolder<UIDeviceImp>> mSparseArray = new SparseArray<>();

    public MyModulesAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIDeviceImp> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected int getLayout(int i)
    {
        return R.layout.list_my_module;
    }

    @Override
    public void onBindViewHolder(DataBindingHolder<UIDeviceImp> holder, UIDeviceImp uiModuleImp, int position)
    {
        mSparseArray.put(uiModuleImp.getId(), holder);

        holder.getViewDataBinding().setVariable(BR.data, uiModuleImp);
        holder.getViewDataBinding().setVariable(BR.itemClickListener, this);
        holder.getViewDataBinding().executePendingBindings();
    }

    public void setEditMode(boolean editMode)
    {
        //        if (mEditMode != editMode)
        //        {
        //            if (mEditMode)
        //            {
        //                for (int i = 0; i < mSparseArray.size(); i++)
        //                {
        //                    int id = mSparseArray.keyAt(i);
        //
        //                    String name = (mSparseArray.get(id)).getEditText();
        //
        //                    if (!TextUtils.isEmpty(name))
        //                    {
        //                        AppManager.getInstance().setModuleName(id, name);
        //                    }
        //                }
        //            }
        //
        //            for (UIModuleImp module : getDataList())
        //            {
        //                module.setEditMode(editMode);
        //            }
        //
        //            mEditMode = editMode;
        //
        //            notifyDataSetChanged();
        //        }
    }
}
