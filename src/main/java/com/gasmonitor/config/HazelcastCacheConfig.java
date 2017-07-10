package com.gasmonitor.config;

import com.gasmonitor.utils.GasEventProcessor;
import com.gasmonitor.utils.InfluxdbService;
import com.hazelcast.config.Config;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.config.TopicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyangminggen on 2017/7/4.
 */
@Configuration
//@Profile("hazelcast-cache")
//@EnableConfigurationProperties(DataProperties.class)
public class HazelcastCacheConfig {
    private Logger logger = LoggerFactory.getLogger(HazelcastCacheConfig.class);

    @Value("${datastore.dbName}")
    private String dbName;
    @Value("${datastore.retention}")
    private String retention;
    @Value("${datastore.batchNum}")
    private int batchNum;
    @Value("${datastore.userName}")
    private String userName;
    @Value("${datastore.password}")
    private String password;
    @Value("${datastore.url}")
    private String url;
    @Value("${datastore.topicName}")
    private String topicName;

    @Autowired
    InfluxdbService influxdbService;

    @Bean
    InfluxdbService influxdbService() {
        InfluxdbService influxdbService = new InfluxdbService();
        influxdbService.initPara(url, dbName, userName, password, retention, batchNum);
        return influxdbService;
    }

    @Bean
    public Config hazelCastConfig() {

        Config config = new Config();
        config.setInstanceName("hazelcastInstance");

        GasEventProcessor processor = new GasEventProcessor(influxdbService);
        ListenerConfig listenerConfig = new ListenerConfig(processor);
        List<ListenerConfig> listenerConfigList = new ArrayList<ListenerConfig>();
        listenerConfigList.add(listenerConfig);
        TopicConfig allEventsCache = new TopicConfig();
        allEventsCache.setMessageListenerConfigs(listenerConfigList);
        allEventsCache.setName("GasEvent");
        logger.info("HazelcastCacheConfig --> 得到的topicName:{}", topicName);
        config.getTopicConfigs().put(topicName, allEventsCache);

        return config;
    }

}