package com.android.smartlink.ui.model;

import android.support.annotation.IntDef;

import com.android.smartlink.util.ui.ImageResUtil;

import static com.android.smartlink.ui.model.BaseModule.Module.GroupType.GROUP_1;
import static com.android.smartlink.ui.model.BaseModule.Module.GroupType.GROUP_2;
import static com.android.smartlink.ui.model.BaseModule.Module.GroupType.GROUP_ALL;
import static com.android.smartlink.ui.model.BaseModule.Module.ImageType.DRAWABLE_LARGE;
import static com.android.smartlink.ui.model.BaseModule.Module.ImageType.DRAWABLE_LARGE_LIGHT;
import static com.android.smartlink.ui.model.BaseModule.Module.ImageType.DRAWABLE_NORMAL;
import static com.android.smartlink.ui.model.BaseModule.Module.ImageType.DRAWABLE_NORMAL_LIGHT;

/**
 * User: LIUWEI
 * Date: 2017-11-09
 * Time: 11:44
 */
public interface BaseModule
{
    int getSlaveID();

    int getChannel();

    String getName();

    interface Module extends BaseModule
    {
        @IntDef({DRAWABLE_NORMAL, DRAWABLE_NORMAL_LIGHT, DRAWABLE_LARGE, DRAWABLE_LARGE_LIGHT})
        @interface ImageType
        {
            int DRAWABLE_NORMAL = 0;
            int DRAWABLE_NORMAL_LIGHT = 1;
            int DRAWABLE_LARGE = 2;
            int DRAWABLE_LARGE_LIGHT = 3;
        }

        @IntDef({GROUP_ALL, GROUP_1, GROUP_2})
        @interface GroupType
        {
            int GROUP_ALL = 0;
            int GROUP_1 = 1;
            int GROUP_2 = 2;
        }

        int getId();

        int getImageRes();
    }

    class DefaultBaseModuleImp<T extends BaseModule> implements Module
    {
        final int mModuleId;

        final BaseModule mModule;

        public DefaultBaseModuleImp(BaseModule baseModule)
        {
            mModule = baseModule;

            mModuleId = ModuleParser.generateId(baseModule.getSlaveID(), baseModule.getChannel());
        }

        @Override
        public final int getSlaveID()
        {
            return mModule.getSlaveID();
        }

        @Override
        public final int getChannel()
        {
            return mModule.getChannel();
        }

        @Override
        public String getName()
        {
            return mModule.getName();
        }

        public final int getId()
        {
            return mModuleId;
        }

        @Override
        public int getImageRes()
        {
            return ImageResUtil.getImage(getId(), ImageType.DRAWABLE_NORMAL);
        }

        public final T getSource()
        {
            //noinspection unchecked
            return (T) mModule;
        }
    }

    class ModuleParser
    {
        private static final int HIGH_BIT = 8;

        private static final int LOW_BIT = 32 - HIGH_BIT;

        private static final int MASK_SALVE_ID = 0xFF000000;

        private static final int MASK_CHANNEL_ID = 0x00FFFFFF;

        public static int generateId(BaseModule module)
        {
            return generateId(module.getSlaveID(), module.getChannel());
        }

        public static int generateId(int salveId, int channel)
        {
            return (salveId << LOW_BIT) & MASK_SALVE_ID | channel;
        }

        public static int parseSalveId(int id)
        {
            return (id & MASK_SALVE_ID) >> LOW_BIT;
        }

        public static int parseChannelId(int id)
        {
            return id & MASK_CHANNEL_ID;
        }
    }
}
