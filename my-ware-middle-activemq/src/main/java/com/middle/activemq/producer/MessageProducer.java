package com.middle.activemq.producer;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Queue;

/**
 * 生产者
 * @author 郑晨
 * @date 2019/10/27 10:00
 * @updateUser
 * @updateDate
 * @updateRemark
 * @version 1.0
 */
@Component
@EnableScheduling
public class MessageProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    private static int num = 1;
    @Scheduled(fixedDelay=3000)//每3s执行1次
    public void send() {
        num++;
        try {
            MapMessage mapMessage = new ActiveMQMapMessage();
            mapMessage.setString("info", "你还在睡觉----"+(num));

            this.jmsMessagingTemplate.convertAndSend(this.queue, mapMessage);
            System.out.println("生产者：生产消息--"+(num));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }













}
