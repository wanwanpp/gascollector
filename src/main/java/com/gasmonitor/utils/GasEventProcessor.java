package com.gasmonitor.utils;

import com.gasmonitor.entity.GasEvent;
import com.gasmonitor.entity.GasHazelcast;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 */
public class GasEventProcessor implements MessageListener<GasHazelcast> {

    private Logger log = LoggerFactory.getLogger(GasEventProcessor.class);
    private InfluxdbService influxdbService;
    private ExecutorService executorService;

    public GasEventProcessor(InfluxdbService influxdbService, int threadNum) {
        log.info("threaadNumber is :{}", threadNum);
        this.influxdbService = influxdbService;
        this.executorService = Executors.newFixedThreadPool(threadNum);
    }

    /**
     * @see com.hazelcast.core.MessageListener#onMessage(com.hazelcast.core.Message)
     */
    @Override
    public void onMessage(Message<GasHazelcast> arg0) {
        GasHazelcast hazelcastEvent = arg0.getMessageObject();
        GasEvent event = hazelcastEvent.getGasEvent();
        String tenantId = hazelcastEvent.getTenantId();
//        executorService.execute(new Consume(event, tenantId));

        //同步写入
        influxdbService.writeToInfluxdb(event, "measurement" + tenantId);
    }

    //异步写
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