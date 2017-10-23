package com.android.smartlink.ui.widget.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.smartlink.R;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-23
 * Time: 16:35
 */
public class SuggestPagerAdapter extends PagerAdapter
{
    private String[] mDataArray;

    private LayoutInflater mInflater;

    public SuggestPagerAdapter(Activity activity)
    {
        mInflater = LayoutInflater.from(activity);

        //// TODO: 2017/10/23 ,now just get freezer suggest!
        mDataArray = activity.getResources().getStringArray(R.array.energy_suggest_freezer);
    }

    @Override
    public int getCount()
    {
        return mDataArray.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        TextView textView = (TextView) mInflater.inflate(R.layout.list_item_engener_suggest, container, false);

        textView.setText(mDataArray[position]);

        container.addView(textView);

        return textView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        if (object != null)
        {
            container.removeView((View) object);
        }
    }
}
