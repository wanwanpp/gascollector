一、运行与编译环境
JDK1.8 tomcat8 influxdb 8080端口不被其他应用占用。如果采用mqtt通讯还需安装apollo.在配置文件中将相应属性设置。
二、编译与运行
1.在项目文件下运行mvn clean install
2.成功后将target目录下的gasmonitor-0.0.1-SNAPSHOT.war改成gasmonitor.war
3。将gasmonitor.war拷贝到tomcat webapps目录下，然后启动tomcat
三、系统架构介绍
1、系统主要为燃气流量计上传数据中间件。系统采用RESTFUL和MQTT两种通信协议。
2、采用RESTFUL协议在前段使用post方法上传数据，上传时将Header的content-type设置成application/json,json内容如下：
    "hardwareId":"设备编号",
    "temperatur":"测点温度",
    "pressure":"测点压力",
   "standard":"标况流量",
   "running":"工况流量",
   "summary":"总计流量",
   "pointtime":"测点时间戳"

3、采用MQTT协议时使用src\main\java\com\gasmonitor\collector\EventMessage.proto文件结构进行protobuf编码后传送
。
4、不管采用RESTFUL还是MQTT，接受数据后写入hazelcast,写入的hazelcast为GasEvent事件加上设备所属的tenant号。变为GasHazelcast类。
5、前段展现和后台写入段都监听此hazecast的topic,写入部分根据tenant号写入gasEvent+tenant的measurement.
6、EventController.java接受Restful上传数据，也向前段展现部分提供历史数据的查询。
测试
四、运行注意事项
1、将GasService中的public void mockHazelcastMap()注释掉，此处是为了模拟设备归属租户建立的一个测试表，和实际情况不同！！！！！！

2。在application.propeties中添加hazelcast，influxdb参数