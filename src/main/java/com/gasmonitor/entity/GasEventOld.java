package com.gasmonitor.entity;

import java.io.Serializable;

public class GasEventOld implements Serializable {
    private String hardwareId;//设备id
    private double temperature;//温度
    private double pressure;//压强
    private double standard;//标况
    private double running;//工况
    private double summary;//累计流量
    private double surplus;//剩余流量
    private double analog1;//
    private double analog2;
    private double analog3;
    private double analog4;
    private int switch1;
    private int switch2;
    private int switch3;
    private int switch4;
    private int ac220;//
    private double battery;
    private double solar;
    private long pointtime;//测点时间

    public String getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getStandard() {
        return standard;
    }

    public void setStandard(double standard) {
        this.standard = standard;
    }

    public double getRunning() {
        return running;
    }

    public void setRunning(double running) {
        this.running = running;
    }

    public double getSummary() {
        return summary;
    }

    public void setSummary(double summary) {
        this.summary = summary;
    }

    public double getSurplus() {
        return surplus;
    }

    public void setSurplus(double surplus) {
        this.surplus = surplus;
    }

    public double getAnalog1() {
        return analog1;
    }

    public void setAnalog1(double analog1) {
        this.analog1 = analog1;
    }

    public double getAnalog2() {
        return analog2;
    }

    public void setAnalog2(double analog2) {
        this.analog2 = analog2;
    }

    public double getAnalog3() {
        return analog3;
    }

    public void setAnalog3(double analog3) {
        this.analog3 = analog3;
    }

    public double getAnalog4() {
        return analog4;
    }

    public void setAnalog4(double analog4) {
        this.analog4 = analog4;
    }

    public int getSwitch1() {
        return switch1;
    }

    public void setSwitch1(int switch1) {
        this.switch1 = switch1;
    }

    public int getSwitch2() {
        return switch2;
    }

    public void setSwitch2(int switch2) {
        this.switch2 = switch2;
    }

    public int getSwitch3() {
        return switch3;
    }

    public void setSwitch3(int switch3) {
        this.switch3 = switch3;
    }

    public int getSwitch4() {
        return switch4;
    }

    public void setSwitch4(int switch4) {
        this.switch4 = switch4;
    }

    public int getAc220() {
        return ac220;
    }

    public void setAc220(int ac220) {
        this.ac220 = ac220;
    }

    public double getBattery() {
        return battery;
    }

    public void setBattery(double battery) {
        this.battery = battery;
    }

    public double getSolar() {
        return solar;
    }

    public void setSolar(double solar) {
        this.solar = solar;
    }

    public long getPointtime() {
        return pointtime;
    }

    public void setPointtime(long pointtime) {
        this.pointtime = pointtime;
    }

    @Override
    public String toString() {
        return "GasEventOld{" +
                "hardwareId='" + hardwareId + '\'' +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", standard=" + standard +
                ", running=" + running +
                ", summary=" + summary +
                ", surplus=" + surplus +
                ", analog1=" + analog1 +
                ", analog2=" + analog2 +
                ", analog3=" + analog3 +
                ", analog4=" + analog4 +
                ", switch1=" + switch1 +
                ", switch2=" + switch2 +
                ", switch3=" + switch3 +
                ", switch4=" + switch4 +
                ", ac220=" + ac220 +
                ", battery=" + battery +
                ", solar=" + solar +
                ", pointtime=" + pointtime +
                '}';
    }
}
