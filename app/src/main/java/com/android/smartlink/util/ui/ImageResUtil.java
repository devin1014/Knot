package com.android.smartlink.util.ui;

import android.support.annotation.DrawableRes;
import android.util.SparseArray;

import com.android.smartlink.R;
import com.android.smartlink.bean.IModuleAddress.ModuleAddress;
import com.android.smartlink.ui.model.BaseModule.Module.ImageType;

public class ImageResUtil
{
    private static final SparseArray<Integer[]> mImageResourceSparse = new SparseArray<>(30);

    static
    {
        final Integer[] defaultImages = new Integer[]{R.drawable.module_default,
                R.drawable.module_default_light,
                R.drawable.module_default,
                R.drawable.module_default_light};

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_MAIN),
                new Integer[]{
                        R.drawable.module_electrical_large,
                        R.drawable.module_electrical_large_white,
                        R.drawable.module_electrical_large,
                        R.drawable.module_electrical_large_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_0),
                new Integer[]{
                        R.drawable.module_lamp,
                        R.drawable.module_lamp_white,
                        R.drawable.module_lamp,
                        R.drawable.module_lamp_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_1),
                new Integer[]{
                        R.drawable.module_lamp,
                        R.drawable.module_lamp_white,
                        R.drawable.module_lamp,
                        R.drawable.module_lamp_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_2),
                new Integer[]{
                        R.drawable.module_chazuo,
                        R.drawable.module_chazuo_white,
                        R.drawable.module_chazuo,
                        R.drawable.module_chazuo_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_3),
                new Integer[]{
                        R.drawable.module_freezer,
                        R.drawable.module_freezer_white,
                        R.drawable.module_freezer,
                        R.drawable.module_freezer_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_4),
                new Integer[]{
                        R.drawable.module_fan,
                        R.drawable.module_fan_white,
                        R.drawable.module_fan,
                        R.drawable.module_fan_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_5),
                new Integer[]{
                        R.drawable.module_electrical,
                        R.drawable.module_electrical_white,
                        R.drawable.module_electrical_large,
                        R.drawable.module_electrical_large_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_6),
                new Integer[]{
                        R.drawable.module_oven,
                        R.drawable.module_oven_white,
                        R.drawable.module_oven,
                        R.drawable.module_oven_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_7),
                new Integer[]{
                        R.drawable.module_tong,
                        R.drawable.module_tong_white,
                        R.drawable.module_tong,
                        R.drawable.module_tong_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_8),
                new Integer[]{
                        R.drawable.module_stove,
                        R.drawable.module_stove_white,
                        R.drawable.module_stove,
                        R.drawable.module_stove_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_9),
                new Integer[]{
                        R.drawable.module_snow,
                        R.drawable.module_snow_white,
                        R.drawable.module_snow,
                        R.drawable.module_snow_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_10),
                new Integer[]{
                        R.drawable.module_freezer,
                        R.drawable.module_freezer_white,
                        R.drawable.module_freezer,
                        R.drawable.module_freezer_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_11),
                new Integer[]{
                        R.drawable.module_electrical,
                        R.drawable.module_electrical_white,
                        R.drawable.module_electrical_large,
                        R.drawable.module_electrical_large_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_12),
                new Integer[]{
                        R.drawable.module_oven,
                        R.drawable.module_oven_white,
                        R.drawable.module_oven,
                        R.drawable.module_oven_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_255_0),
                new Integer[]{
                        R.drawable.module_lamp,
                        R.drawable.module_lamp_white,
                        R.drawable.module_lamp,
                        R.drawable.module_lamp_white});
        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_255_1), new Integer[]{
                R.drawable.module_tong,
                R.drawable.module_tong_white,
                R.drawable.module_tong,
                R.drawable.module_tong_white});
        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_255_2), new Integer[]{
                R.drawable.module_lamp,
                R.drawable.module_lamp_white,
                R.drawable.module_lamp,
                R.drawable.module_lamp_white});
        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_255_3), defaultImages);
        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_255_4), defaultImages);
        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_255_5), defaultImages);
        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_255_6), defaultImages);
        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_255_7), defaultImages);
        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_255_8), defaultImages);
        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_255_9), defaultImages);

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_1_0), defaultImages);
        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_1_1), defaultImages);
    }

    @DrawableRes
    public static int getImage(int id, @ImageType int type)
    {
        Integer[] array = mImageResourceSparse.get(id);

        if (array != null && array.length == 4)
        {
            return array[type];
        }

        return R.drawable.ic_logo;
    }
}
