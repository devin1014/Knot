package com.android.smartlink.util;

/**
 * Created by GerryTan on 2017/10/21.
 */
import android.util.Log;

import java.io.DataInputStream;
import java.util.Date;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.msg.ModbusRequest;
import com.serotonin.modbus4j.msg.ModbusResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import com.serotonin.modbus4j.msg.WriteRegistersRequest;
import com.serotonin.modbus4j.msg.WriteRegistersResponse;
import com.serotonin.util.queue.ByteQueue;


public class ModbusHelp {
    public static void modbusWTCP(String ip, int port, int slaveId, int start, short[] values)
    {
        ModbusFactory modbusFactory = new ModbusFactory();
        // 设备ModbusTCP的Ip与端口，如果不设定端口则默认为502
        IpParameters params = new IpParameters();
        params.setHost(ip);
        if (502 != port) {
            params.setPort(port);
        }// 设置端口，默认502
        ModbusMaster tcpMaster = null;
        // 参数1：IP和端口信息 参数2：保持连接激活
        tcpMaster = modbusFactory.createTcpMaster(params, true);
        try {
            tcpMaster.init();
            Log.d("init", "modbusWTCP: ");
        } catch (ModbusInitException e) {
            Log.d("exceptionrr", e.getMessage());
        }
        try {
            WriteRegistersRequest request = new WriteRegistersRequest(slaveId, start, values);
            WriteRegistersResponse response = (WriteRegistersResponse) tcpMaster.send(request);
            if (response.isException())
                Log.d("Exceptiontt", response.getExceptionMessage());
            else
                Log.d("ok", "ok ");
        } catch (ModbusTransportException e) {
            Log.d("Exception", e.getMessage());
        }
    }

    public static String modbusRTCP(String ip, int port,int[] IDArr) {
        String resultStr="{\"data\":[";
        ModbusFactory modbusFactory = new ModbusFactory();
        // 设备ModbusTCP的Ip与端口，如果不设定端口则默认为502
        IpParameters params = new IpParameters();
        params.setHost(ip);
        if(502!=port){params.setPort(port);}//设置端口，默认502
        ModbusMaster tcpMaster = null;
        tcpMaster = modbusFactory.createTcpMaster(params, true);
        try {
            tcpMaster.init();
        } catch (ModbusInitException e) {
            Log.d("Exception", e.getMessage());
            return null;
        }
        String tJson="";
        ByteQueue tqueue;
        byte[] tempArr;
        float tCurrent;
        float tVoltage;
        float tPower;
        float tPF;
        float tEnergy;
        int tAlarm;
       for(int i=0;i<IDArr.length;i++)
       {
           tCurrent=0;
           tVoltage=0;
           tPower=0;
           tPF=0;
           tEnergy=0;
           tAlarm=0;

           tJson="";
           tqueue=readData(tcpMaster,IDArr[i],2999,6);
           tempArr=tqueue.popAll();
           if(tempArr.length>14) {
            tCurrent=byteToFloat(tempArr[3],tempArr[4],tempArr[5],tempArr[6]);
               tCurrent+=byteToFloat(tempArr[7],tempArr[8],tempArr[9],tempArr[10]);
               tCurrent+=byteToFloat(tempArr[11],tempArr[12],tempArr[13],tempArr[14]);
           }
           tqueue=readData(tcpMaster,IDArr[i],3027,6);
           tempArr=tqueue.popAll();
           if(tempArr.length>14) {
               tVoltage=byteToFloat(tempArr[3],tempArr[4],tempArr[5],tempArr[6]);
               tVoltage+=byteToFloat(tempArr[7],tempArr[8],tempArr[9],tempArr[10]);
               tVoltage+=byteToFloat(tempArr[11],tempArr[12],tempArr[13],tempArr[14]);
           }
           tqueue=readData(tcpMaster,IDArr[i],3059,2);
           tempArr=tqueue.popAll();
           if(tempArr.length>6)
           tPower=byteToFloat(tempArr[3],tempArr[4],tempArr[5],tempArr[6])/1000;

           tqueue=readData(tcpMaster,IDArr[i],3083,2);
           tempArr=tqueue.popAll();
           if(tempArr.length>6)
               tPF=byteToFloat(tempArr[3],tempArr[4],tempArr[5],tempArr[6]);

           tqueue=readData(tcpMaster,IDArr[i],3203,4);
           Log.d("energy",tqueue.toString());
           tempArr=tqueue.popAll();
           if(tempArr.length>10) {
               tEnergy = (((long)(tempArr[7])&0xFF)<<24) + (((long)(tempArr[8])&0xFF)<<16) + (((long)(tempArr[9])&0xFF)<<8) + ((long)(tempArr[10])&0xFF);
               tEnergy/=1000;
           }

           tqueue=readData(tcpMaster,IDArr[i],3299,1);
           tempArr=tqueue.popAll();
           if(tempArr.length>4)
               tAlarm=(int)tempArr[4];

            tJson="{\"slaveID\":"+"\""+IDArr[i]+"\",";
           tJson+="\"current\":"+"\""+tCurrent+"\",";
           tJson+="\"voltage\":"+"\""+tVoltage+"\",";
           tJson+="\"power\":"+"\""+tPower+"\",";
           tJson+="\"powerFactor\":"+"\""+tPF+"\",";
           tJson+="\"energy\":"+"\""+tEnergy+"\",";
           tJson+="\"alarm\":"+"\""+tAlarm+"\"";
           if(i!=IDArr.length-1)
            resultStr+=tJson+"},";
           else
               resultStr+=tJson+"}";
       }
        resultStr+="]}";

               Log.d("result", resultStr);
        tcpMaster.destroy();
        return resultStr;
    }

    private static ByteQueue readData(ModbusMaster tcpMaster, int slaveID,int registerStart,int rLength)
    {
        ByteQueue byteQueue= new ByteQueue(8+rLength*2);
        ModbusRequest modbusRequest=null;
        try {
            modbusRequest = new ReadHoldingRegistersRequest(slaveID, registerStart, rLength);//功能码03
        } catch (ModbusTransportException e) {
            Log.d("Exception", e.getMessage());
        }
        ModbusResponse modbusResponse=null;
        try {
            modbusResponse = tcpMaster.send(modbusRequest);
        } catch (ModbusTransportException e) {
            Log.d("Exception", e.getMessage());
        }
        if(modbusResponse!=null) {
            modbusResponse.write(byteQueue);
        }
        return byteQueue;

    }
    private  static float byteToFloat(byte t1,byte t2,byte t3,byte t4)
    {


        float f=0;
        DataInputStream dis=new DataInputStream(new java.io.ByteArrayInputStream(new byte[]{t1,t2,t3,t4}));
        try {
             f = dis.readFloat();
            dis.close();

        }
        catch (Exception ee)
        {
            Log.d("float",ee.getMessage());
            return 0;
        }
        return f;
    }




}
