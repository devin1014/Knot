package com.android.smartlink.util.ui;

import android.support.annotation.DrawableRes;
import android.util.SparseArray;

import com.android.smartlink.R;
import com.android.smartlink.bean.IModuleAddress.ModuleAddress;
import com.android.smartlink.ui.model.UIMonitorModule.ImageType;

public class ImageResUtil
{
    private static final SparseArray<Integer[]> mImageResourceSparse = new SparseArray<>(30);

    static
    {
        final Integer[] defaultImages = new Integer[]{R.drawable.module_default,
                R.drawable.module_default_light,
                R.drawable.module_default,
                R.drawable.module_default_light};

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_0),
                new Integer[]{
                        R.drawable.module_electrical,
                        R.drawable.module_electrical_white,
                        R.drawable.module_electrical_large,
                        R.drawable.module_electrical_large_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_5),
                new Integer[]{
                        R.drawable.module_water_pump,
                        R.drawable.module_water_pump_white,
                        R.drawable.module_water_pump,
                        R.drawable.module_water_pump_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_1),
                new Integer[]{
                        R.drawable.module_freezer,
                        R.drawable.module_freezer_white,
                        R.drawable.module_freezer,
                        R.drawable.module_freezer_white_large});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_4),
                new Integer[]{
                        R.drawable.module_air_conditioner,
                        R.drawable.module_air_conditioner_white,
                        R.drawable.module_air_conditioner,
                        R.drawable.module_air_conditioner_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_3),
                new Integer[]{
                        R.drawable.module_light,
                        R.drawable.module_light_white,
                        R.drawable.module_light,
                        R.drawable.module_light_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_2),
                new Integer[]{
                        R.drawable.module_oven,
                        R.drawable.module_oven_white,
                        R.drawable.module_oven_white_large,
                        R.drawable.module_oven_white_large});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_6),
                new Integer[]{
                        R.drawable.module_elevator,
                        R.drawable.module_elevator_white,
                        R.drawable.module_elevator,
                        R.drawable.module_elevator_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_7),
                new Integer[]{
                        R.drawable.module_others,
                        R.drawable.module_others_white,
                        R.drawable.module_others,
                        R.drawable.module_others_white});

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_8), defaultImages);
        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.MODULE_9), defaultImages);

        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_255_0), defaultImages);
        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_255_1), defaultImages);
        mImageResourceSparse.put(ModuleAddress.parse(ModuleAddress.TOGGLE_255_2), defaultImages);
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
