package com.gasmonitor.controller;

import com.gasmonitor.entity.GasEvent;
import com.gasmonitor.entity.GasEventC;
import com.gasmonitor.service.GasEventService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(path = "/new2", method = {RequestMethod.POST})
    public Object addEvent2(GasEventC event, HttpServletRequest request) throws Exception {
        log.info("attr:{}", MapUtils.toProperties(request.getParameterMap()).toString());
        log.info("接收到的event:{}", event);
        log.info("接收到的硬件id为:{}", event.getHardwareId());
        try {
            if (StringUtils.isEmpty(event.getHardwareId())) {
                return null;
            }
            service.process(event.trans2GasEvent());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
        System.out.println(event);
        return new HashMap<String, String>().put("sucess", "true");
    }

    @RequestMapping(path = "/query", method = {RequestMethod.POST})
    @ResponseBody
    public List<GasEvent> queryEvents(
            @RequestParam(value = "hardwareId", required = true) String hardwareId,
            @RequestParam(value = "begin", required = true) long begin,
            @RequestParam(value = "end", required = true) long end) throws Exception {

        List<GasEvent> events = service.query(hardwareId, begin, end);

        return events;
    }

}
