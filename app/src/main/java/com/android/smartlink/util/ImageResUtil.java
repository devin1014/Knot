package com.android.smartlink.util;

import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.util.SparseArray;

import com.android.smartlink.R;
import com.android.smartlink.bean.IModuleAddress;

import java.lang.annotation.Retention;

import static com.android.smartlink.util.ImageResUtil.ImageType.DRAWABLE_LARGE;
import static com.android.smartlink.util.ImageResUtil.ImageType.DRAWABLE_LARGE_LIGHT;
import static com.android.smartlink.util.ImageResUtil.ImageType.DRAWABLE_NORMAL;
import static com.android.smartlink.util.ImageResUtil.ImageType.DRAWABLE_NORMAL_LIGHT;
import static java.lang.annotation.RetentionPolicy.SOURCE;

public class ImageResUtil
{
    private static final SparseArray<Integer[]> mImageResourceSparse = new SparseArray<>(30);

    static
    {
        mImageResourceSparse.put(IModuleAddress.MONITOR_0,
                new Integer[]{
                        R.drawable.module_electrical,
                        R.drawable.module_electrical_white,
                        R.drawable.module_electrical_white_large,
                        R.drawable.module_electrical_white_large});

        mImageResourceSparse.put(IModuleAddress.MONITOR_1,
                new Integer[]{
                        R.drawable.module_water_pump,
                        R.drawable.module_water_pump_white,
                        R.drawable.module_water_pump,
                        R.drawable.module_water_pump_white});

        mImageResourceSparse.put(IModuleAddress.MONITOR_2,
                new Integer[]{
                        R.drawable.module_freezer,
                        R.drawable.module_freezer_white,
                        R.drawable.module_freezer_white_large,
                        R.drawable.module_freezer_white_large});

        mImageResourceSparse.put(IModuleAddress.MONITOR_3,
                new Integer[]{
                        R.drawable.module_air_conditioner,
                        R.drawable.module_air_conditioner_white,
                        R.drawable.module_air_conditioner,
                        R.drawable.module_air_conditioner_white});

        mImageResourceSparse.put(IModuleAddress.MONITOR_4,
                new Integer[]{
                        R.drawable.module_light,
                        R.drawable.module_light_white,
                        R.drawable.module_light,
                        R.drawable.module_light_white});

        mImageResourceSparse.put(IModuleAddress.MONITOR_5,
                new Integer[]{
                        R.drawable.module_oven,
                        R.drawable.module_oven_white,
                        R.drawable.module_oven_white_large,
                        R.drawable.module_oven_white_large});

        mImageResourceSparse.put(IModuleAddress.MONITOR_6,
                new Integer[]{
                        R.drawable.module_elevator,
                        R.drawable.module_elevator_white,
                        R.drawable.module_elevator,
                        R.drawable.module_elevator_white});

        mImageResourceSparse.put(IModuleAddress.MONITOR_7,
                new Integer[]{
                        R.drawable.module_others,
                        R.drawable.module_others_white,
                        R.drawable.module_others,
                        R.drawable.module_others_white});
    }

    @Retention(SOURCE)
    @IntDef({DRAWABLE_NORMAL, DRAWABLE_NORMAL_LIGHT, DRAWABLE_LARGE, DRAWABLE_LARGE_LIGHT})
    public @interface ImageType
    {
        int DRAWABLE_NORMAL = 0;
        int DRAWABLE_NORMAL_LIGHT = 1;
        int DRAWABLE_LARGE = 2;
        int DRAWABLE_LARGE_LIGHT = 3;
    }

    @DrawableRes
    public static int getImage(int id, @ImageType int type)
    {
        Integer[] array = mImageResourceSparse.get(id);

        if (array != null)
        {
            int res = array[type];

            if (res > 0)
            {
                return res;
            }
        }

        return R.drawable.ic_logo;
    }
}
