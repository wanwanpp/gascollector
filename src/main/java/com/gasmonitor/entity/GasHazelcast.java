 package com.gasmonitor.entity;

 import java.io.Serializable;

 public class GasHazelcast implements Serializable{


     private String tenantId ;
       public void setTenantId(String tenantId)
      {
          this.tenantId=tenantId;
       }
       public String getTenantId()
       {
         return tenantId;
         }
       private GasEvent gasEvent ;
       public void setGasEvent(GasEvent gasEvent )
      {
          this.gasEvent=gasEvent;
       }
       public GasEvent getGasEvent()
       {
         return gasEvent;
         }



 }
