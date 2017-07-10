package com.gasmonitor.controller;

import com.gasmonitor.entity.GasEvent;
import com.gasmonitor.service.GasEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    @Autowired
    GasEventService service;

    @RequestMapping(path = "/new", method = {RequestMethod.POST})
    public Object addEvent(@RequestBody GasEvent event) throws Exception {
//    public Object addEvent(@RequestParam String event) throws Exception {
        try {
            service.process(event);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
        System.out.println(event);
        return new HashMap<String, String>().put("sucess", "true");
    }

    @RequestMapping(path = "/query", method = {RequestMethod.POST})
    public List<GasEvent> queryEvents(
            @RequestParam(value = "hardwareId", required = true) String hardwareId,
            @RequestParam(value = "begin", required = true) String begin, @RequestParam(value = "begin", required = true) String end) throws Exception {
        Timestamp bb=new Timestamp(Long.parseLong(begin));
        Timestamp ee=new Timestamp(Long.parseLong(end)); 
        System.out.println("The begin time i s"+bb+" and end is "+ee);
        List<GasEvent> events = service.query(hardwareId, bb, ee);
        System.out.println("The query result size is "+events.size());
        return events;
    }

}
