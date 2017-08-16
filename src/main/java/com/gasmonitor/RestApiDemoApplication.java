package com.gasmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableIntegration
@EnableAsync
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
