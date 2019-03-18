package com.android.smartlink.assist;

import android.support.annotation.NonNull;

import com.android.smartlink.util.ModbusHelp;

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
                String serverAddress = "192.168.1.4";
                final int port = 502;

                // write data should +1
                //String modbusStatus = ModbusHelp.modbusWDefaultTCP(serverAddress, port, deviceId, channelId + 1, status);
                //LogUtil.log(BaseExecutorService.this, modbusStatus);

                {
                    //String tmyIp = "192.168.1.4";
                    //控制回路开关，id=255,通道1，2，3，4，5，7，控制地址14201,14241,14281,14321,14361,14441
                    //控制回路开关，id=1,通道7，9,控制地址14441,14521
                    //2打开，1关闭
                    String tStr = ModbusHelp.modbusWDefaultTCP(serverAddress, port, deviceId, channelId + 1, status);
                }
            }
        });
    }
}
