 package com.gasmonitor.test;

 import com.gasmonitor.entity.GasEventOld;
import com.gasmonitor.utils.ProtoBuffferUtil;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

 public class PushCallback implements MqttCallback {


    public PushCallback(){}

     public void connectionLost(Throwable cause) {
         System.out.println("断开连接"+cause);
     }

     public void deliveryComplete(IMqttDeliveryToken token) {
     }

     public void messageArrived(String topic, MqttMessage message) throws Exception {
           GasEventOld event= ProtoBuffferUtil.decoding(message.getPayload());
       System.out.println("hardid="+event.getHardwareId());

    }
 }