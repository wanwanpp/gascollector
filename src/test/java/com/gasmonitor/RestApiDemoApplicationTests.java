package com.gasmonitor;

import com.gasmonitor.entity.GasEvent;
import com.gasmonitor.utils.InfluxdbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestApiDemoApplicationTests {

	@Autowired
	InfluxdbService influxdbService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void query(){
//		List<GasEvent> eventList = influxdbService.query("999", "ID2", new Timestamp(System.currentTimeMillis() - 60 * 60 * 1000), new Timestamp(System.currentTimeMillis()));
		List<GasEvent> eventList = influxdbService.query("999", "ID2", System.currentTimeMillis() - 24*60 * 60 * 1000, System.currentTimeMillis());
		System.out.println(eventList.size());
	}

}
