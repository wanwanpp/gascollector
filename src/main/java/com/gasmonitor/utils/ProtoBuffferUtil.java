 package com.gasmonitor.utils;
   
import com.google.protobuf.InvalidProtocolBufferException;
import com.gasmonitor.collector.EventProbuf;
import com.gasmonitor.entity.GasEvent;
/**  
 
 */    
public class ProtoBuffferUtil{  
  
    public static byte[]  codedByte(String id,double temp,double pressure,double standard,double running,double sum,long pointTime) {  
         EventProbuf.EventMessage.Builder builder = 
                         EventProbuf.EventMessage.newBuilder();
         builder.setHardwareId(id);
         builder.setTemperature(temp);
         builder.setPressure(pressure);
         builder.setStandard(standard);
         builder.setRunning(running);
         builder.setSummary(sum);
         builder.setPointtime(pointTime);
         EventProbuf.EventMessage e = builder.build();
         return  e.toByteArray();
       
    }  
    public static byte[]  codedByte(GasEvent e) {  
         EventProbuf.EventMessage.Builder builder = 
                         EventProbuf.EventMessage.newBuilder();
         builder.setHardwareId(e.getHardwareId());
         builder.setTemperature(e.getTemperature());
         builder.setPressure(e.getPressure());
         builder.setStandard(e.getStandard());
         builder.setRunning(e.getRunning());
         builder.setSummary(e.getSummary());
         builder.setPointtime(e.getPointtime());
         EventProbuf.EventMessage message= builder.build();
         return  message.toByteArray();
       
    }  
    public static  GasEvent decoding(byte[] buf){
     GasEvent event =new GasEvent();
     try {
       EventProbuf.EventMessage message= EventProbuf.EventMessage.parseFrom(buf); 
        
         event.setHardwareId(message.getHardwareId());
         event.setTemperature(message.getTemperature());
         event.setPressure(message.getPressure());
         event.setStandard(message.getStandard());
         event.setRunning(message.getRunning());
         event.setSummary(message.getSummary());
         event.setPointtime(message.getPointtime());
 
        } catch (InvalidProtocolBufferException excep) {
            // TODO Auto-generated catch block
            excep.printStackTrace();
       }
     return event;
}
}