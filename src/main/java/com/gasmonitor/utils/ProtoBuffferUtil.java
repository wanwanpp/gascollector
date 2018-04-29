package com.gasmonitor.utils;

import com.gasmonitor.entity.GasEvent;
import com.gasmonitor.entity.GasEventOld;
import com.google.protobuf.InvalidProtocolBufferException;

/**

 */
public class ProtoBuffferUtil {

    public static byte[] codedByte(String hardwareId, double temperature, double pressure, double standard,
                                   double running, double summary,double surplus,double analog1,double analog2,
                                   double analog3,double analog4, int switch1,int switch2,int switch3,int switch4,
                                   int ac220,int battery,int solar,long pointTime) {
        GasEvent.EventMessage.Builder builder = GasEvent.EventMessage.newBuilder();
        builder.setHardwareId(hardwareId);
        builder.setTemperature(temperature);
        builder.setPressure(pressure);
        builder.setStandard(standard);
        builder.setRunning(running);
        builder.setSummary(summary);
        builder.setSurplus(surplus);
        builder.setAnalog1(analog1);
        builder.setAnalog2(analog2);
        builder.setAnalog3(analog3);
        builder.setAnalog4(analog4);
        builder.setSwitch1(switch1);
        builder.setSwitch2(switch2);
        builder.setSwitch3(switch3);
        builder.setSwitch4(switch4);
        builder.setAc220(ac220);
        builder.setBattery(battery);
        builder.setSolar(solar);
        builder.setPointtime(pointTime);
        GasEvent.EventMessage e = builder.build();
        return e.toByteArray();
    }

    public static byte[] codedByte(GasEventOld e) {
        GasEvent.EventMessage.Builder builder = GasEvent.EventMessage.newBuilder();
        builder.setHardwareId(e.getHardwareId());
        builder.setTemperature(e.getTemperature());
        builder.setPressure(e.getPressure());
        builder.setStandard(e.getStandard());
        builder.setRunning(e.getRunning());
        builder.setSummary(e.getSummary());
        builder.setSurplus(e.getSurplus());
        builder.setAnalog1(e.getAnalog1());
        builder.setAnalog2(e.getAnalog2());
        builder.setAnalog3(e.getAnalog3());
        builder.setAnalog4(e.getAnalog4());
        builder.setSwitch1(e.getSwitch1());
        builder.setSwitch2(e.getSwitch2());
        builder.setSwitch3(e.getSwitch3());
        builder.setSwitch4(e.getSwitch4());
        builder.setAc220(e.getAc220());
        builder.setBattery(e.getBattery());
        builder.setSolar(e.getSolar());
        builder.setPointtime(e.getPointtime());
        GasEvent.EventMessage message = builder.build();
        return message.toByteArray();

    }

    public static GasEventOld decoding(byte[] buf) {
        GasEventOld event = new GasEventOld();
        try {
            GasEvent.EventMessage message = GasEvent.EventMessage.parseFrom(buf);

            event.setHardwareId(message.getHardwareId());
            event.setTemperature(message.getTemperature());
            event.setPressure(message.getPressure());
            event.setStandard(message.getStandard());
            event.setRunning(message.getRunning());
            event.setSummary(message.getSummary());
            event.setSurplus(message.getSurplus());
            event.setAnalog1(message.getAnalog1());
            event.setAnalog2(message.getAnalog2());
            event.setAnalog3(message.getAnalog3());
            event.setAnalog4(message.getAnalog4());
            event.setSwitch1(message.getSwitch1());
            event.setSwitch2(message.getSwitch2());
            event.setSwitch3(message.getSwitch3());
            event.setSwitch4(message.getSwitch4());
            event.setAc220(message.getAc220());
            event.setBattery(message.getBattery());
            event.setSolar(message.getSolar());
            event.setPointtime(message.getPointtime());

        } catch (InvalidProtocolBufferException excep) {
            // TODO Auto-generated catch block
            excep.printStackTrace();
        }
        return event;
    }
}