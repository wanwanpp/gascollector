package com.gasmonitor.utils;
 
import org.influxdb.*;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.List;
import org.influxdb.dto.BatchPoints; 
import org.influxdb.dto.Point;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import com.gasmonitor.entity.*;
import org.apache.commons.collections.CollectionUtils;
import org.influxdb.dto.QueryResult;

public class InfluxdbUtil{  
    static String url="http://172.23.253.31:8086";
    static String dbName = "gasEvent";
    static int count=0;
    static String user="root";
    static String password="root";
    static String retention="default";
    static  InfluxDB influxDB;// = InfluxDBFactory.connect(url, user, password);
    static BatchPoints batchPoints;
    static int begin=0;
    static int batchNum=2000;


    public static int writeToInfluxdb(GasEvent e,String measurement) {  
          
     Point point = Point.measurement(measurement)  
            .time(e.getPointtime(), TimeUnit.MILLISECONDS)  
            .tag("hardwareId", e.getHardwareId())  
            .addField("temperature",e.getTemperature())  
            .addField("pressure",e.getPressure())
            .addField("standard",e.getStandard())
            .addField("running",e.getRunning())
            .addField("summary",e.getSummary())
            .build();  

      // Flush every 2000 Points, at least every 1000ms
     if ( begin ==0)
     {
     influxDB.enableBatch(batchNum, 100, TimeUnit.MILLISECONDS);
     begin=1;
     }
    
    if (count ==0)
         batchPoints = BatchPoints  
            .database(dbName)  
            //.tag("async", "true") //Add a tag to this set of points.  
            .retentionPolicy(retention)  
            .consistency(InfluxDB.ConsistencyLevel.ALL)  
            .build();  
    batchPoints.point(point);
    count++;
    if (count ==batchNum){  
    influxDB.write(batchPoints);  
    count =0;
    }
    return 0;
     
    }  

 
 //init
public static void initPara(String url1,String dbName1,String user1,String   password1,String retention1,int batchNum1)  {
     url=url1;
     dbName=dbName1;
     user=user1;
     password=password1;
     batchNum=batchNum1;
     retention=retention1;
     influxDB = InfluxDBFactory.connect(url, user, password);

  }//end of initPara
 
}//end of class