package com.gasmonitor.utils;

import com.gasmonitor.entity.GasEventOld;
import com.gasmonitor.entity.GasHazelcast;
import com.gasmonitor.service.InfluxdbService;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GasEventProcessor implements MessageListener<GasHazelcast> {

    private Logger log = LoggerFactory.getLogger(GasEventProcessor.class);
    private InfluxdbService influxdbService;

    public GasEventProcessor(InfluxdbService influxdbService) {
        this.influxdbService = influxdbService;
    }

    @Override
    public void onMessage(Message<GasHazelcast> arg0) {
        GasHazelcast hazelcastEvent = arg0.getMessageObject();
        GasEventOld event = hazelcastEvent.getGasEventOld();
        String tenantId = hazelcastEvent.getTenantId();
        //同步写入
        influxdbService.writeToInfluxdb(event, "measurement" + tenantId);
    }
}