package com.gasmonitor.service;

import com.gasmonitor.entity.GasEventOld;
import org.apache.commons.collections.CollectionUtils;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InfluxdbService {
    private Logger log = LoggerFactory.getLogger(InfluxdbService.class);
    @Value("${datastore.url}")
    String url;
    @Value("${datastore.dbName}")
    String dbName;
    @Value("${datastore.userName}")
    String user;
    @Value("${datastore.password}")
    String password;
    @Value("${datastore.retention}")
    String retention;
    @Value("${datastore.batchNum}")
    int batchNum;


    InfluxDB influxDB;//= InfluxDBFactory.connect(url, user, password);
    BatchPoints batchPoints;


    public int writeToInfluxdb(GasEventOld e, String measurement) {

        // Flush every 2000 Points, at least every 1000ms
        //2000个point或者过1000ms就flush一次
//         influxDB.enableBatch(2000, 1000, TimeUnit.MILLISECONDS);

        Point point = Point.measurement(measurement)
                .time(e.getPointtime(), TimeUnit.MILLISECONDS)
                .addField("temperature", e.getTemperature())
                .addField("pressure", e.getPressure())
                .addField("standard", e.getStandard())
                .addField("running", e.getRunning())
                .addField("summary", e.getSummary())
                .addField("surplus", e.getSurplus())
                .addField("analog1", e.getAnalog1())
                .addField("analog2", e.getAnalog2())
                .addField("analog3", e.getAnalog3())
                .addField("analog4", e.getAnalog4())
                .addField("switch1", e.getSwitch1())
                .addField("switch2", e.getSwitch2())
                .addField("switch3", e.getSwitch3())
                .addField("switch4", e.getSwitch4())
                .addField("ac220", e.getAc220())
                .addField("battery", e.getBattery())
                .addField("solar", e.getSolar())
                .tag("hardwareId", e.getHardwareId())
                .build();
        log.info("The point is {}", point);

        batchPoints = BatchPoints
                .database(dbName)
                .retentionPolicy(retention)
                .consistency(InfluxDB.ConsistencyLevel.ALL)        //设置一致性水平
                .build();
        batchPoints.point(point);
        influxDB.write(batchPoints);
        return 0;
    }

    //query measurements
    public List<GasEventOld> query(String tenant, String hardwareId, long begin, long end) {
        begin *= 1000000;
        end *= 1000000;
        String command = "select * from measurement" + tenant.trim() + "  where hardwareId='" + hardwareId + "' and time>" + begin + " and time<" + end;
        System.out.println("\n ===========================================\nthe influxdb command is " + command);

        LinkedList<GasEventOld> list = new LinkedList<>();
        QueryResult queryResult = influxDB.query(new Query(command, dbName));
        List<QueryResult.Result> results = queryResult.getResults();
        if (CollectionUtils.isNotEmpty(results.get(0).getSeries())) {
            System.out.println("\n the series is:" + results.get(0).getSeries().get(0).getValues().get(0).get(0));
            List<List<Object>> objList = results.get(0).getSeries().get(0).getValues();
            int size = objList.size();
            System.out.println("The size is " + size);

            for (int i = 0; i < size; i++) {
                GasEventOld event = new GasEventOld();
                event.setHardwareId(hardwareId);

                List<Object> fieldList = objList.get(i);
//                log.info("查询到的objlist:{}", objList);
                String splitStr = (String) fieldList.get(0);
                splitStr = splitStr.replace('T', ' ');
                splitStr = splitStr.substring(0, splitStr.length() - 1);
                System.out.println("Time str is:" + splitStr);
                Timestamp timestamp = Timestamp.valueOf(splitStr);
                long pointtime = timestamp.getTime();
                event.setPointtime(pointtime);
                int ac220 = (int) (double) fieldList.get(1);
                event.setAc220(ac220);
                double analog1 = (double) fieldList.get(2);
                event.setAnalog1(analog1);
                double analog2 = (double) fieldList.get(3);
                event.setAnalog2(analog2);
                double analog3 = (double) fieldList.get(4);
                event.setAnalog3(analog3);
                double analog4 = (double) fieldList.get(5);
                event.setAnalog4(analog4);
                int battery = (int) (double) fieldList.get(6);
                event.setBattery(battery);
                double pressure = (double) fieldList.get(8);
                event.setPressure(pressure);
                double running = (double) fieldList.get(9);
                event.setRunning(running);
                int solar = (int) (double) fieldList.get(10);
                event.setSolar(solar);
                double standard = (double) fieldList.get(11);
                event.setStandard(standard);
                double summary = (double) fieldList.get(12);
                event.setSummary(summary);
                double surplus = (double) fieldList.get(13);
                event.setSurplus(surplus);
                int switch1 = (int) (double) fieldList.get(14);
                event.setSwitch1(switch1);
                int switch2 = (int) (double) fieldList.get(15);
                event.setSwitch2(switch2);
                int switch3 = (int) (double) fieldList.get(16);
                event.setSwitch3(switch3);
                int switch4 = (int) (double) fieldList.get(17);
                event.setSwitch4(switch4);
                double temperature = (double) fieldList.get(18);
                event.setTemperature(temperature);

                System.out.println(event);
                list.add(event);
            }
        } else {
            System.out.println("查询结果为空。");
        }
        return list;
    }


    //init
    public void initPara(String url1, String dbName1, String user1, String password1, String retention1, int batchNum1) {
        log.info("开始初始化 influxdb的参数url1:{},dbName1:{},muser1:{},password1:{}.retention1:{},batchNum1:{}",
                url1, dbName1, user1, password1, retention1, batchNum1);
        url = url1;
        dbName = dbName1;
        user = user1;
        password = password1;
        batchNum = batchNum1;
        retention = retention1;
        influxDB = InfluxDBFactory.connect(url, user, password);
    }//end of initPara

}//end of class