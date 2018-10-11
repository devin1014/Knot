package com.android.smartlink.ui.widget.adapter;

import android.app.Activity;
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
    public static final int[] SUGGESTIONS = new int[]{R.array.energy_suggest_1, R.array.energy_suggest_2, R.array.energy_suggest_3,
            R.array.energy_suggest_4, R.array.energy_suggest_5, R.array.energy_suggest_6};

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
