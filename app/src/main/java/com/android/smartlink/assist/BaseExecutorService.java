package com.android.smartlink.assist;

import android.support.annotation.NonNull;

import com.android.smartlink.util.ModbusHelp;
import com.neulion.core.util.LogUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * User: LIUWEI
 * Date: 2017-12-28
 * Time: 11:16
 */
public class BaseExecutorService
{
    private static final ThreadFactory sThreadFactory = new ThreadFactory()
    {
        int index = 0;

        @Override
        public Thread newThread(@NonNull Runnable r)
        {
            Thread thread = new Thread(r);

            thread.setName("BaseExecutorService#Thread" + index);

            return thread;
        }
    };

    private static final ExecutorService sExecutor = Executors.newCachedThreadPool(sThreadFactory);

    public final void execute(final int channelId, final int deviceId, final int status)
    {
        sExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                String serverAddress = "192.168.1.100";

                final int port = 502;

                // write data should +1
                String modbusStatus = ModbusHelp.modbusWDefaultTCP(serverAddress, port, deviceId, channelId + 1, status);

                LogUtil.log(BaseExecutorService.this, modbusStatus);
            }
        });
    }
}
