package com.gasmonitor.utils;

import com.gasmonitor.entity.GasEvent;
import org.apache.commons.collections.CollectionUtils;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InfluxdbService {
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

    InfluxDB influxDB;// = InfluxDBFactory.connect(url, user, password);
    BatchPoints batchPoints;


    public int writeToInfluxdb(GasEvent e, String measurement) {

        // Flush every 2000 Points, at least every 1000ms
        // influxDB.enableBatch(1, 1, TimeUnit.MILLISECONDS);

        Point point = Point.measurement(measurement)
                .time(e.getPointtime(), TimeUnit.MILLISECONDS)
                .addField("temperature", e.getTemperature())
                .addField("pressure", e.getPressure())
                .addField("standard", e.getStandard())
                .addField("running", e.getRunning())
                .addField("summary", e.getSummary())
                .tag("hardwareId", e.getHardwareId())
                .build();
        System.out.println("The point is " + point);

        batchPoints = BatchPoints
                .database(dbName)
                .retentionPolicy(retention)
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
        batchPoints.point(point);
        influxDB.write(batchPoints);
        return 0;
    }

    //query measurements
//    public List<GasEvent> query(String tenant, String hardwareId, Timestamp begin, Timestamp end) {
    public List<GasEvent> query(String tenant, String hardwareId, long begin, long end) {
        begin*=1000000;
        end*=1000000;
//        String command = "select * from measurement" + tenant.trim() + "  where hardwareId='" + hardwareId + "' and time>'" + begin + "' and time<'" + end + "'";
        String command = "select * from measurement" + tenant.trim() + "  where hardwareId='" + hardwareId + "' and time>" + begin + " and time<" + end;
        ;

        // String command="select * from measurement99"+"  where hardwareId='ID2'";//+hardwareId+"'";

        //+"  and time>"+begin+" and time<"+end;
        System.out.println("\n ===========================================\nthe influxdb command is " + command);
        ArrayList<GasEvent> list = new ArrayList<GasEvent>();
        QueryResult queryResult = influxDB.query(new Query(command, dbName));
        List<QueryResult.Result> results = queryResult.getResults();
        System.out.println("\n the series is:" + results.get(0).getSeries().get(0).getValues().get(0).get(0));
        List<List<Object>> objList = results.get(0).getSeries().get(0).getValues();
        if (CollectionUtils.isNotEmpty(results)) {
            int size = objList.size();
            System.out.println("The size is " + size);
            for (int i = 0; i < size; i++) {
                GasEvent event = new GasEvent();
                event.setHardwareId(hardwareId);
                String splitStr = (String) objList.get(i).get(0);
                splitStr = splitStr.replace('T', ' ');
                splitStr = splitStr.substring(0, splitStr.length() - 1);
                System.out.println("Time str is:" + splitStr);
                Timestamp timestamp = Timestamp.valueOf(splitStr);
                long pointtime = timestamp.getTime();
                System.out.println("time is " + pointtime);
                //long pointtime = Long.parseLong(splitStr);
                event.setPointtime(pointtime);
                //splitStr=(String)objList.get(i).get(6);
                //System.out.println("value is "+splitStr);
                Double tmp = (Double) objList.get(i).get(6);
                event.setTemperature(tmp);
                tmp = (Double) objList.get(i).get(2);
                System.out.println("value is " + tmp);
                event.setPressure(tmp);
                tmp = (Double) objList.get(i).get(4);
                System.out.println("4value is " + tmp);
                event.setStandard(tmp);
                tmp = (Double) objList.get(i).get(3);
                System.out.println("3value is " + tmp);
                event.setRunning(tmp);
                tmp = (Double) objList.get(i).get(5);
                System.out.println("5value is " + tmp);
                event.setSummary(tmp);

                list.add(event);
            }
        }
        return list;
    }


    //init
    public void initPara(String url1, String dbName1, String user1, String password1, String retention1, int batchNum1) {
        url = url1;
        dbName = dbName1;
        user = user1;
        password = password1;
        batchNum = batchNum1;
        retention = retention1;
        influxDB = InfluxDBFactory.connect(url, user, password);

    }//end of initPara

}//end of class