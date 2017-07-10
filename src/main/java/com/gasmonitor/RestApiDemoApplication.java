package com.gasmonitor;

import java.util.List;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.integration.config.EnableIntegration;

import org.springframework.context.annotation.Bean;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.HazelcastClient;

import com.gasmonitor.utils.GasEventProcessor;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.config.TopicConfig;
import com.hazelcast.config.Config;

@SpringBootApplication
@EnableCaching
@EnableIntegration
public class RestApiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiDemoApplication.class, args);
    }
    /*
	@Bean
	public HazelcastInstance hazelcastInstance(){
            ClientConfig config=new ClientConfig();
            HazelcastInstance instance=HazelcastClient.newHazelcastClient(config);
		return instance;
		};*/


}
