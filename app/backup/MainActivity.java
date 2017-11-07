package com.weihantec.gerrytan.homelink;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    String tStr="";
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        final EditText et = (EditText) findViewById(R.id.editText1);
        ImageView iv = (ImageView) findViewById(R.id.imageView3);

        final Handler handler= new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {

                et.setText(tStr);
            }
        };

      final   Thread t = new Thread(){
        @Override
            public void run() {
            String myIp = "192.168.1.101";
            int myPort = 502;
            int[] idArr = new int[]{150, 151, 152};
            tStr = ModbusHelp.modbusRTCP(myIp, 502, idArr);
            handler.post(runnable);
            }
            };


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               t.start();
            }
        });






    }
}

