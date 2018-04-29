package com.gasmonitor.test;

import com.gasmonitor.entity.GasEventOld;
import com.gasmonitor.utils.ProtoBuffferUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Random;

public class AsynServer {

    public static final String HOST = "tcp://localhost:61613";
//    public static final String HOST = "tcp://172.23.253.42:61613";

    public static final String TOPIC = "tokudu/yzq124";
    private static final String clientid = "server1";

    //private MqttClient client;
    private MqttTopic topic;
    private String userName = "admin";
    private String passWord = "password";
    private final MqttAsyncClient client;
    private MqttMessage message;
    private MqttConnectOptions options;
    private boolean available = false;

    public AsynServer() throws MqttException {
        client = new MqttAsyncClient(HOST, clientid, new MemoryPersistence());
        options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        options.setConnectionTimeout(1000000);
        options.setMaxInflight(30000);
        options.setKeepAliveInterval(20);
        IMqttToken mqttToken = client.connect(options);
        mqttToken.waitForCompletion(10000);
        if (mqttToken.isComplete()) {
            if (mqttToken.getException() != null) {
                // TODO: retry
            }
        }
        client.setCallback(new PushCallback());

    }


    public void publish(byte[] payload) throws MqttException {
        try {

            client.publish(TOPIC, payload, 1, false);
            System.out.println("Sent payload...");
            //  TimeUnit.SECONDS.sleep(5);
        } catch (MqttException e) {
            e.printStackTrace();
            //sleep(500);
        }
    }

//    public static void main(String[] args) throws MqttException, InterruptedException {
//        AsynServer server = new AsynServer();
//        server.message = new MqttMessage();
//        server.message.setQos(2);
//        server.message.setRetained(false);
////new instance of gasEvent
//        GasEventOld event = new GasEventOld();
//        int count = 0;
//        while (count < 10000) {
////        while (true) {
//            event.setHardwareId("ID" + count % 10);
//            event.setTemperature(count);
//            event.setPressure(count * 2 + 1);
//            event.setStandard(count * 3 + 2);
//            event.setRunning(count * 4 + 3);
//            event.setSummary(count * 5 + 4);
//            event.setSurplus(count * 5 + 4);
//            event.setAnalog1(count * 5 + 4);
//            event.setAnalog2(count * 5 + 4);
//            event.setAnalog3(count * 5 + 4);
//            event.setAnalog4(count * 5 + 4);
//            event.setSwitch1(count * 5 + 4);
//            event.setSwitch2(count * 5 + 4);
//            event.setSwitch3(count * 5 + 4);
//            event.setSwitch4(count * 5 + 4);
//            event.setAc220(count * 5 + 4);
//            event.setBattery(count * 5 + 4);
//            event.setSolar(count * 5 + 4);
//            event.setPointtime(System.currentTimeMillis());
//            count++;
//            server.publish(ProtoBuffferUtil.codedByte(event));
//            Thread.sleep(1000);
////           if(count%200==0){
////               Thread.sleep(1000);
////           }
//        }//end of loop
////        System.out.println(server.message.isRetained() + "------ratained״̬");
//    }
public static void main(String[] args) throws MqttException, InterruptedException {
    AsynServer server = new AsynServer();
    server.message = new MqttMessage();
    server.message.setQos(2);
    server.message.setRetained(false);

    GasEventOld event = new GasEventOld();
    int count = 0;
    Random random = new Random();
    while (count < 10000) {
//        while (true) {
        event.setHardwareId("ID" + count % 10);
        event.setTemperature(count);
        event.setPressure(random.nextInt(100));
        event.setStandard(random.nextInt(100));
        event.setRunning(random.nextInt(100));
        event.setSummary(random.nextInt(100));
        event.setSurplus(random.nextInt(100));
        event.setAnalog1(random.nextInt(100));
        event.setAnalog2(random.nextInt(100));
        event.setAnalog3(random.nextInt(100));
        event.setAnalog4(random.nextInt(100));
        event.setSwitch1(random.nextInt(100));
        event.setSwitch2(random.nextInt(100));
        event.setSwitch3(random.nextInt(100));
        event.setSwitch4(random.nextInt(100));
        event.setAc220(random.nextInt(100));
        event.setBattery(random.nextInt(100));
        event.setSolar(random.nextInt(100));
        event.setPointtime(System.currentTimeMillis());
        count++;
        server.publish(ProtoBuffferUtil.codedByte(event));
        Thread.sleep(1000);
//           if(count%200==0){
//               Thread.sleep(1000);
//           }
    }//end of loop
//        System.out.println(server.message.isRetained() + "------ratained״̬");
}

}
