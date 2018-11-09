package com.android.smartlink.ui.model;

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

    class ModuleParser
    {
        private static final int HIGH_BIT = 8;

        private static final int LOW_BIT = 32 - HIGH_BIT;

        private static final int MASK_SALVE_ID = 0xFF000000;

        private static final int MASK_CHANNEL_ID = 0x00FFFFFF;

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

    class DefaultBaseModuleImp implements BaseModule
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
    }

    interface SourceModule<T extends BaseModule> extends BaseModule
    {
        int getImageRes();

        T getSource();
    }

    abstract class DefaultSourceModuleImp<T extends BaseModule> extends DefaultBaseModuleImp implements SourceModule<T>
    {
        DefaultSourceModuleImp(BaseModule module)
        {
            super(module);
        }

        @SuppressWarnings("unchecked")
        public final T getSource()
        {
            return (T) mModule;
        }
    }
}