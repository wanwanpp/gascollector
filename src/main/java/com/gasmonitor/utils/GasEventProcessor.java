package com.gasmonitor.utils;

import com.gasmonitor.entity.GasEvent;
import com.gasmonitor.entity.GasHazelcast;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 */
public class GasEventProcessor implements MessageListener<GasHazelcast> {

    private InfluxdbService influxdbService;
    private ExecutorService executorService;

    public GasEventProcessor(InfluxdbService influxdbService,int threadNum) {
        System.out.println("threaadNumber is "+threadNum);
        this.influxdbService = influxdbService;
        executorService = Executors.newFixedThreadPool(threadNum);
    }

    /**
     * @see com.hazelcast.core.MessageListener#onMessage(com.hazelcast.core.Message)
     */
    @Override
    // TODO: 2017/7/10 0010 添加多线程，使用ExecutorService
    public void onMessage(Message<GasHazelcast> arg0) {
        GasHazelcast hazelcastEvent = arg0.getMessageObject();
        //display(hazelcastEvent);
        GasEvent event = hazelcastEvent.getGasEvent();
        String tenantId = hazelcastEvent.getTenantId();
        executorService.execute(new Consume(event,tenantId));
//        influxdbService.writeToInfluxdb(event, "measurement" + tenantId);
        System.out.println("write into influxdb successfully!!!=========================================");
    }

    public void display(GasHazelcast hazelcastEvent) {
        System.out.println("The listener received data......................................");
        System.out.println("the tenant id is " + hazelcastEvent.getTenantId());
        GasEvent event = hazelcastEvent.getGasEvent();
        System.out.println("The hardwareid is :" + event.getHardwareId());
        System.out.println("The temperature is:" + event.getTemperature());
        System.out.println("THE PRESSURE IS :" + event.getPressure());
        System.out.println("the standard is :" + event.getStandard());
        System.out.println("The running is :" + event.getRunning());
        System.out.println("The pointtime is :" + event.getPointtime());
    }

    public class Consume implements Runnable {

        private GasEvent event;
        private String tenantId;

        public Consume(GasEvent event, String tenantId) {
            this.event = event;
            this.tenantId = tenantId;
        }

        @Override
        public void run() {
            influxdbService.writeToInfluxdb(event, "measurement" + tenantId);
        }
    }
}