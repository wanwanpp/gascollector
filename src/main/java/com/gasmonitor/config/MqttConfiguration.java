package com.gasmonitor.config;

import com.gasmonitor.entity.GasEvent;
import com.gasmonitor.service.GasEventService;
import com.gasmonitor.utils.ProtoBuffferUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

//import org.springframework.integration.Message;
//import org.springframework.integration.MessagingException;

//@Configuration //暂时不用mqtt
//@EnableConfigurationProperties(MqttProperties.class) //暂时不用
public class MqttConfiguration {

    @Autowired
    GasEventService gasEventService;

    @Value("${mqtt.userName}")
    private String userName;
    @Value("${mqtt.password}")
    private String password;
    @Value("${mqtt.brokerURL}")
    private String brokerURL;
    @Value("${mqtt.topicName}")
    private String topicName;


    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DefaultPahoMessageConverter messageConverter() {
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
        converter.setPayloadAsBytes(true);
        return converter;
    }

    @Bean
    public MessageProducer inbound() {
        DefaultMqttPahoClientFactory clientFact = new DefaultMqttPahoClientFactory();
        clientFact.setUserName(userName);
        clientFact.setPassword(password);
        MqttPahoMessageDrivenChannelAdapter inbound =
                new MqttPahoMessageDrivenChannelAdapter(brokerURL, "si-test-in", clientFact,
                        topicName);
        inbound.setCompletionTimeout(30000);
        inbound.setConverter(messageConverter());
        //inbound.setConverter(new ByteArrayMessageConverter());

        inbound.setQos(1);
        inbound.setOutputChannel(mqttInputChannel());
        /*ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    taskScheduler.initialize();
	inbound.setTaskScheduler(taskScheduler);
	inbound.setBeanFactory(mock(BeanFactory.class));
	inbound.afterPropertiesSet();
	inbound.start();
    	*/
        return inbound;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                byte[] bytes = null;
                Object payload = message.getPayload();
                try {
                    if (payload instanceof byte[]) {
                        bytes = (byte[]) payload;
                    } else if (payload instanceof String)
                        bytes = ((String) payload).getBytes();
                    GasEvent event = ProtoBuffferUtil.decoding(bytes);
                    gasEventService.process(event);
                } catch (Exception e) {
                    System.out.println("\nError in receiving data from mqtt broker!!!," + e.toString());
                }
            }

        };
    }

}


