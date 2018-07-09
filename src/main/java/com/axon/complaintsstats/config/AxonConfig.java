package com.axon.complaintsstats.config;

import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.serialization.Serializer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@Configuration
public class AxonConfig {
    @Bean
    public SpringAMQPMessageSource statisticsQueue(Serializer serializer) {
        return new SpringAMQPMessageSource(new DefaultAMQPMessageConverter(serializer)) {
            @RabbitListener(queues = "Complaints")
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                super.onMessage(message, channel);
                System.out.println("msg1 ok");
            }
        };
    }

    @Bean
    public SpringAMQPMessageSource statisticsQueue2(Serializer serializer) {
        return new SpringAMQPMessageSource(new DefaultAMQPMessageConverter(serializer)) {
            @RabbitListener(queues = "Complaints2")
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                super.onMessage(message, channel);
                System.out.println("msg2 ok");
            }
        };
    }

    /**
     * 配置文件上传临时路径
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = System.getProperty("user.dir") + "/data/tmp";
        File tmpFile = new File(location);
        if(!tmpFile.exists()){
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return  factory.createMultipartConfig();
    }
}
