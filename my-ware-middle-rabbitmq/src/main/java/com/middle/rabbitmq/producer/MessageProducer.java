package com.middle.rabbitmq.producer;

import com.middle.rabbitmq.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
    private Logger logger = LoggerFactory.getLogger(MessageProducer.class) ;
    @Autowired
    private AmqpTemplate rabbitTemplate;

    //发送一个直连的消息

    public void sendHello(String message){
        //logger.info("sendHello发送："+message);
        for(int i=0;i<10;i++){
            rabbitTemplate.convertAndSend(RabbitMQConfig.HELLO_QUEUE,message+i);
        }
    }

    public void sendHello_1(String message){
        //logger.info("sendHello发送："+message);
        for(int i=0;i<10;i++){
            rabbitTemplate.convertAndSend(RabbitMQConfig.HELLO_QUEUE_1,message+i);
        }
    }


    public void sendHello_2(String message) {
        for(int i=0;i<10;i++){
            rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE,"",message+i);
        }
    }

    public void sendHello_3(String message) {
        for(int i=0;i<10;i++){
            rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE,"rout_1",message+i);
        }
    }

    public void sendHello_4(String message) {
        for(int i=0;i<10;i++){
            rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE,"rout_2",message+i);
        }
    }

    //topic
    public void sendHello_5(String message) {
        for(int i=0;i<10;i++){
            rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE,"topic.out.in","topic.out.in "+message+i);
        }
    }

    public void sendHello_6(String message) {
        for(int i=0;i<10;i++){
            rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE,"topic.in","topic.in "+message+i);
        }
    }

    public void sendHello_8(String message) {
        for(int i=0;i<10;i++){
            rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE,"topic.in","topic.in.in "+message+i);
        }
    }

    public void sendHello_7(String message) {
        for(int i=0;i<10;i++){
            rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE,"topic.msg",message+i);
        }
    }

}
