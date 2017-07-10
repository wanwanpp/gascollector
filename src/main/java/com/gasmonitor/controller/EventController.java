package com.gasmonitor.controller;

import com.gasmonitor.entity.GasEvent;
import com.gasmonitor.service.GasEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(value = "begin", required = true) long begin, @RequestParam(value = "end", required = true) long end) throws Exception {

        List<GasEvent> events = service.query(hardwareId, begin, end);

        return events;
    }

}
