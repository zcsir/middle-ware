package com.middle.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;

/**
 * 消费者
 * @author 郑晨
 * @date 2019/10/27 10:35
 * @updateUser
 * @updateDate
 * @updateRemark
 * @version 1.0
 */
@Component
public class MessageConsumer {

    //使用JmsListener配置消费者监听的队列，其中Message是接收到的消息
    @JmsListener(destination = "active.queue")
    public void receiveQueue(Message message) {
        try {
            MapMessage mapMessage = (MapMessage) message;
            String info = mapMessage.getString("info");
            System.out.println("消费者1消费----"+info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //使用JmsListener配置消费者监听的队列，其中Message是接收到的消息
    @JmsListener(destination = "active.queue")
    public void receiveQueue1(Message message) {
        try {
            MapMessage mapMessage = (MapMessage) message;
            String info = mapMessage.getString("info");
            System.out.println("消费者2消费----"+info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
