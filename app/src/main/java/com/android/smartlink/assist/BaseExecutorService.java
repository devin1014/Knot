package com.android.smartlink.assist;

import android.support.annotation.NonNull;

import com.android.smartlink.util.ModbusHelp;
import com.neulion.core.util.LogUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
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

    public final void execute(final int channelId, final int status)
    {
        sExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                String serverAddress = "192.168.1.101";

                final int port = 502;

                String modbusStatus = ModbusHelp.modbusWDefaultTCP(serverAddress, port, channelId, status);

                LogUtil.log(BaseExecutorService.this, modbusStatus);
            }
        });
    }
}
