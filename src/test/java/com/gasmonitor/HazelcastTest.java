package com.gasmonitor;

import com.gasmonitor.entity.GasHazelcast;
import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TopicConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import org.junit.Test;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastInstanceFactory;

/**
 * @author 王萍
 * @date 2018/2/14 0014
 */
public class HazelcastTest {

    @Test
    public void testHazelServer() throws InterruptedException {
        Config config = new Config();
        config.setInstanceName("hazelcastInstance");
        NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setPublicAddress("localhost");
        config.setNetworkConfig(networkConfig);
//        GasEventProcessor processor = new GasEventProcessor(influxdbService, threadNum);
//        ListenerConfig listenerConfig = new ListenerConfig(processor);
//        List<ListenerConfig> listenerConfigList = new ArrayList<ListenerConfig>();
//        listenerConfigList.add(listenerConfig);
        TopicConfig topicConfig = new TopicConfig();
//        topicConfig.setMessageListenerConfigs(listenerConfigList);
        topicConfig.setName("GasEventOld");
//        config.getTopicConfigs().put(topicName, topicConfig);

        HazelcastInstance hazelcastInstance = (new HazelcastInstanceFactory(config)).getHazelcastInstance();
        ITopic<GasHazelcast> topic = hazelcastInstance.getTopic("gasEvent");
        GasHazelcast gasHazelcast = new GasHazelcast();
        gasHazelcast.setTenantId("123456");
        topic.publish(gasHazelcast);

        Thread.sleep(111111111);
    }

    @Test
    public void testHazelClient() throws InterruptedException {
        Config config = new Config("hazelcastInstance");
        NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setPublicAddress("localhost");
        config.setNetworkConfig(networkConfig);

        HazelcastInstance hazelcastInstance = (new HazelcastInstanceFactory(config)).getHazelcastInstance();
        ITopic<GasHazelcast> topic = hazelcastInstance.getTopic("gasEvent");
        topic.addMessageListener(new MessageListener<GasHazelcast>() {
            @Override
            public void onMessage(Message<GasHazelcast> message) {
                GasHazelcast hazelcastEvent = message.getMessageObject();
                System.out.println(hazelcastEvent);
            }
        });

        Thread.sleep(111111111);
    }
}
