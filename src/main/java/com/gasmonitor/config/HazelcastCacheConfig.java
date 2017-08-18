package com.gasmonitor.config;

import com.gasmonitor.utils.GasEventProcessor;
import com.gasmonitor.utils.InfluxdbService;
import com.hazelcast.config.Config;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TopicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyangminggen on 2017/7/4.
 */
@Configuration
//@Profile("hazelcast-cache")
@EnableConfigurationProperties(DataProperties.class)
public class HazelcastCacheConfig {

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

    @Value("${influx.write.threadNum}")
    private int threadNum;

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
//        config.setProperty("hazelcast.initial.min.cluster.size","2");
        NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setPublicAddress("0.0.0.0");
        config.setNetworkConfig(networkConfig);

        GasEventProcessor processor = new GasEventProcessor(influxdbService, threadNum);
        ListenerConfig listenerConfig = new ListenerConfig(processor);
        List<ListenerConfig> listenerConfigList = new ArrayList<ListenerConfig>();
        listenerConfigList.add(listenerConfig);
        TopicConfig allEventsCache = new TopicConfig();
        allEventsCache.setMessageListenerConfigs(listenerConfigList);
        allEventsCache.setName("GasEvent");
        config.getTopicConfigs().put(topicName, allEventsCache);

        return config;
    }

}