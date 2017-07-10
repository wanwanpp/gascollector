package com.gasmonitor.utils;

import com.gasmonitor.entity.GasEvent;
import com.gasmonitor.entity.GasHazelcast;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

/**
 */
public class GasEventProcessor implements MessageListener<GasHazelcast> {
    private InfluxdbService influxdbService;

    public GasEventProcessor(InfluxdbService influxdbService) {
        this.influxdbService = influxdbService;
    }

    /**
     * @see com.hazelcast.core.MessageListener#onMessage(com.hazelcast.core.Message)
     */
    @Override
    public void onMessage(Message<GasHazelcast> arg0) {
        GasHazelcast hazelcastEvent = (GasHazelcast) arg0.getMessageObject();
        //display(hazelcastEvent);
        GasEvent event = hazelcastEvent.getGasEvent();
        String tenantId = hazelcastEvent.getTenantId();
        influxdbService.writeToInfluxdb(event, "measurement" + tenantId);
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
}