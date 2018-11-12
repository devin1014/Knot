//package com.android.smartlink.ui.widget.adapter;
//
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import com.android.smartlink.BR;
//import com.neulion.core.widget.recyclerview.adapter.DiffDataBindingAdapter;
//import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;
//
///**
// * User: LIUWEI
// * Date: 2017-10-17
// * Time: 16:24
// */
//abstract class BaseAdapter<T> extends DiffDataBindingAdapter<T>
//{
//    BaseAdapter(LayoutInflater layoutInflater, OnItemClickListener<T> listener)
//    {
//        super(layoutInflater, listener);
//    }
//
//    @Override
//    public DataBindingHolder<T> onCreateHeadHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, int i)
//    {
//        return null;
//    }
//
//    @Override
//    protected final int[] getVariableIds()
//    {
//        return new int[]{BR.data, BR.handler};
//    }
//}
