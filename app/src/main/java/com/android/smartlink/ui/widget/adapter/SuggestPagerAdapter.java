package com.android.smartlink.ui.widget.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;

/**
 * User: LIUWEI
 * Date: 2017-10-23
 * Time: 16:35
 */
public class SuggestPagerAdapter extends PagerAdapter
{
    public static final int[] SUGGESTIONS = new int[]{
            R.array.energy_suggest_1,
            R.array.energy_suggest_2,
            R.array.energy_suggest_3,
            R.array.energy_suggest_4,
            R.array.energy_suggest_5,
            R.array.energy_suggest_6};

    private String[] mDataArray;

    private LayoutInflater mInflater;

    public SuggestPagerAdapter(Activity activity)
    {
        mInflater = LayoutInflater.from(activity);

        mDataArray = activity.getResources().getStringArray(SUGGESTIONS[AppManager.getInstance().getEnergySuggestIndex()]);
    }

    @Override
    public int getCount()
    {
        return mDataArray.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        TextView textView = (TextView) mInflater.inflate(R.layout.adapter_suggest, container, false);

        textView.setText(mDataArray[position]);

        container.addView(textView);

        return textView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((View) object);
    }
}
