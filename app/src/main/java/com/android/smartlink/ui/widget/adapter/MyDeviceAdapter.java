package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIDeviceImp;
import com.neulion.core.widget.recyclerview.adapter.DiffDataBindingAdapter;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

/**
 * User: LIUWEI
 * Date: 2017-10-22
 * Time: 13:51
 */
public class MyDeviceAdapter extends DiffDataBindingAdapter<UIDeviceImp>
{
    //private boolean mEditMode = false;

    //    private SparseArray<DataBindingHolder<UIDeviceImp>> mSparseArray = new SparseArray<>();

    public MyDeviceAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIDeviceImp> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected int getLayout(int i)
    {
        return R.layout.list_my_device;
    }

    //    @Override
    //    public void onBindViewHolder(DataBindingHolder<UIDeviceImp> holder, UIDeviceImp uiModuleImp, int position)
    //    {
    //        super.onBindViewHolder(holder, uiModuleImp, position);
    //        mSparseArray.put(uiModuleImp.getId(), holder);
    //    }

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
