package com.gasmonitor.service;
//--2

import com.gasmonitor.entity.GasEventOld;
import com.gasmonitor.entity.GasHazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.ITopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.List;

@Service
@ConfigurationProperties(prefix = "event")
public class GasEventService {
    private Logger logger = LoggerFactory.getLogger(GasEventService.class);

    /**
     * 会根据有Config参数的那个方法注入。
     */
    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Autowired
    private InfluxdbService influxdbService;
    private ITopic<GasHazelcast> topic;
    private IMap<String, String> map;
    private String topicName = "gasEvent";

    //get the hazelcastInstance's topic--29
    @PostConstruct
    public void initContext() {
        topic = hazelcastInstance.getTopic(topicName);
        map = hazelcastInstance.getMap("tenant");
        mockHazelcastMap();
    }

    @Async
    public Integer process(GasEventOld event) throws Exception {

//        display(event);   //打印操作
        GasHazelcast hazelcastEvent = new GasHazelcast();
        hazelcastEvent.setGasEventOld(event);
        String tenant = map.get(event.getHardwareId());
//        logger.info("把收到的消息-->{} publish--> key:{}==>value:{},map-->{}", hazelcastEvent, event.getHardwareId(), tenant, map.toString());
        if (tenant != null) {
            //添加tenantId
            hazelcastEvent.setTenantId(tenant);
            topic.publish(hazelcastEvent);
        }
        return 1;
    }

    //service for query history oof measurements
//    public List<GasEventOld> query(String hardwareId, Timestamp begin, Timestamp end) {
    public List<GasEventOld> query(String hardwareId, long begin, long end) {
        String tenant = map.get(hardwareId);
        System.out.println("\n========================================================The tenant id is" + tenant);
        return influxdbService.query(tenant, hardwareId, begin, end);
    }

    //to display data
    public void display(GasEventOld event) {
        logger.info("================================================================================The received even is :");
        logger.info("The received even is :{}", event);
    }

    public void mockHazelcastMap() {
        int count = 0;
        while (count < 10000) {
            map.put("ID" + count, "999");
            count++;
        }
    }
}
