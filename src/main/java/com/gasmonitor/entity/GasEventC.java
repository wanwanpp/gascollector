package com.gasmonitor.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class GasEventC implements Serializable {

    private String hardwareId;//设备id  hardwareId=00002112：DTUID，8位数字；
    private String ver; //仪器id
    private String addr;
    private double temperature;//温度 temperature=23.63：温度，-20～100；
    private double pressure;//压强 pressure=311.41：压力，4BYTE
    private double standard;//标况  standard=1160.22：标况，
    private double running;//工况  running=382.06：工况，
    private double summary;//累计流量  summary=6264700.00：累计，0～999999999；
    private double surplus;//剩余流量   surplus=0.00：剩余流量，0～99999999；
    private double analog1;// analog1=1.10：模拟量，4BYTE 双精度；
    private double analog2;//analog2=1.10：模拟量，4BYTE 双精度；
    private double analog3;//analog3=0.71：模拟量，4BYTE 双精度；
    private double analog4; //analog4=0.73：模拟量，4BYTE 双精度；
    private int switch1;// switch1=关：开/关；
    private int switch2;// switch2=关：开/关；
    private int switch3;//switch3=关：开/关；
    private int switch4;//switch4=关：开/关；
    private int ac220;// ac220=有电/无电；
    private double battery;// battery=4.08，4BYTE;
    private double solar;//  solar=0.00:,4BYTE
    private String pointtime;//测点时间  pointtime=2017-10-10 20:33:28，有效时间

    //构造设备id
    public GasEvent trans2GasEvent() {
        GasEvent ret = new GasEvent();
        if (this.getAddr().length() == 1) {
            ret.setHardwareId(this.getHardwareId() + "00" + this.getAddr());
        } else if (ver.length() == 2) {
            ret.setHardwareId(this.getHardwareId() + "0" + this.getAddr());
        } else if (ver.length() == 3) {
            ret.setHardwareId(this.getHardwareId() + "" + this.getAddr());
        } else {
            ret.setHardwareId(this.getHardwareId() + "00" + this.getAddr());
        }
        ret.setTemperature(this.getTemperature());
        ret.setPressure(this.getPressure());
        ret.setStandard(this.getStandard());
        ret.setRunning(this.getRunning());
        ret.setSummary(this.getSummary());
        ret.setSurplus(this.getSurplus());
        ret.setAnalog1(this.getAnalog1());
        ret.setAnalog2(this.getAnalog2());
        ret.setAnalog3(this.getAnalog3());
        ret.setAnalog4(this.getAnalog4());
        ret.setSwitch1(getSwitch1());
        ret.setSwitch2(getSwitch2());
        ret.setSwitch3(getSwitch3());
        ret.setSwitch4(getSwitch4());
        ret.setBattery(this.getBattery());
        ret.setSolar(this.getSolar());
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ret.setPointtime(dateFormat.parse(getPointtime()).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
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

    public int getswitch(String s) {
        if ("开".equalsIgnoreCase(s)) {
            return 1;
        } else {
            return 0;
        }
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

    public String getPointtime() {
        return pointtime;
    }

    public void setPointtime(String pointtime) {
        this.pointtime = pointtime;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    @Override
    public String toString() {
        return "GasEventC{" +
                "hardwareId='" + hardwareId + '\'' +
                ", ver='" + ver + '\'' +
                ", addr='" + addr + '\'' +
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
                ", pointtime='" + pointtime + '\'' +
                '}';
    }
}
