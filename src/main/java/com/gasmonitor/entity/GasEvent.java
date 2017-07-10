 package com.gasmonitor.entity; 
import  java.io.Serializable;
public class GasEvent implements Serializable{

	 
	private String hardwareId ;
      public void setHardwareId(String hardwareId)
     {
         this.hardwareId=hardwareId;
      }
      public String getHardwareId()
      {
        return hardwareId;
        }
      private double temperature ;
      public void setTemperature(double temperature)
     {
         this.temperature=temperature;
      }
      public double getTemperature()
      {
        return temperature;
        }
      private double pressure ;
      public void setPressure(double pressure)
     {
         this.pressure=pressure;
      }
      public double getPressure()
      {
        return pressure;
        }
      
      private double standard ;
      public void setStandard(double standard)
     {
         this.standard=standard;
      }
      public double getStandard()
      {
        return standard;
        }
     private double running ;
      public void setRunning(double running)
     {
         this.running=running;
      }
      public double getRunning()
      {
        return running;
      }
      private double summary ;
      public void setSummary(double summary)
     {
         this.summary=summary;
      }
      public double getSummary()
      {
        return summary;
        }
      private long pointtime ;
      public void setPointtime(long pointtime)
     {
         this.pointtime=pointtime;
      }
      public long getPointtime()
      {
        return pointtime;
        }
       
 

}
