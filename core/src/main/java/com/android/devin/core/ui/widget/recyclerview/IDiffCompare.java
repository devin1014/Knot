package com.android.devin.core.ui.widget.recyclerview;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-10
 * Time: 15:50
 */
public interface IDiffCompare<T>
{
    boolean compareObject(T t);

    boolean compareContent(T t);

    Object getChangePayload();
}
