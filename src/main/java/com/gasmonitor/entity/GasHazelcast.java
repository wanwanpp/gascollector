package com.gasmonitor.entity;

import java.io.Serializable;

public class GasHazelcast implements Serializable {

    private String tenantId;

    private GasEventOld gasEventOld;

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setGasEventOld(GasEventOld gasEventOld) {
        this.gasEventOld = gasEventOld;
    }

    public GasEventOld getGasEventOld() {
        return gasEventOld;
    }
}
