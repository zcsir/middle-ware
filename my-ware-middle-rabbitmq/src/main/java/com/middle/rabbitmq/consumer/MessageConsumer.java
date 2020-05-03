package com.middle.rabbitmq.consumer;

import com.middle.rabbitmq.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageConsumer {
    private Logger logger = LoggerFactory.getLogger(MessageConsumer.class) ;
    /**
     * 直连接的例子
     * @param text
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitMQConfig.HELLO_QUEUE)
    public void receive_hello_one(String text) {
        logger.info("receive_hello_one : {}", text);
    }

    /**
     * 直连接的例子
     * @param text
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitMQConfig.HELLO_QUEUE)
    public void receive_hello_two(String text) {
        logger.info("receive_hello_two : {}", text);
    }


    /**
     * 手动返回ack
     * @param msg
     * @param channel
     * @param message String hello,Channel channel, Message message
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitMQConfig.HELLO_QUEUE_1)
    public void receive_hello_three(String msg, Channel channel, Message message) throws IOException{

        try {
            logger.info("receive_hello_three : {}", msg);
            if(msg.contains("2")||msg.contains("3")){
                throw new Exception();
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            logger.info("业务成功!----{}",msg);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.info("业务失败!----{}",msg);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            //否认消息 否认消息代表放回队列继续消费，有死循环的可能
        }

    }


    @RabbitHandler
    @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = RabbitMQConfig.PUB_QUEUE,durable = "true"),
            exchange =@Exchange(value = RabbitMQConfig.FANOUT_EXCHANGE,durable = "true",type = "fanout"),key = "")})
    public void receive_hello_Four(String msg, Channel channel, Message message) throws Exception{
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            logger.info("receive_hello_Four 成功!----{}",msg);
        } catch (Exception e) {
            logger.info("receive_hello_Four 失败!----{}",msg);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }

    }

    @RabbitHandler
    @RabbitListener(queues = RabbitMQConfig.SUB_QUEUE)
    public void receive_hello_Five(String msg, Channel channel, Message message) throws Exception {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            logger.info("receive_hello_Five 成功!----{}",msg);
        } catch (Exception e) {
            logger.info("receive_hello_Five 失败!----{}",msg);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }
    }


    @RabbitHandler
    @RabbitListener(queues = RabbitMQConfig.ROUT_QUEUE)
    public void receive_rout_all(String msg, Channel channel, Message message) throws Exception {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            logger.info("receive_rout_all 成功!----{}",msg);
        } catch (Exception e) {
            logger.info("receive_rout_all 失败!----{}",msg);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }
    }

    @RabbitHandler
    @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = RabbitMQConfig.ROUT_QUEUE,durable = "true"),
            exchange =@Exchange(value = RabbitMQConfig.DIRECT_EXCHANGE,durable = "true",type = "direct"),key = "rout_1")})
    public void receive_rout_one(String msg, Channel channel, Message message) throws Exception {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            logger.info("receive_rout_one 成功!----{}",msg);
        } catch (Exception e) {
            logger.info("receive_rout_one 失败!----{}",msg);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }
    }

    @RabbitHandler
    @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = RabbitMQConfig.ROUT_QUEUE,durable = "true"),
            exchange =@Exchange(value = RabbitMQConfig.DIRECT_EXCHANGE,durable = "true",type = "direct"),key = "rout_3")})
    public void receive_rout_two(String msg, Channel channel, Message message) throws Exception {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            logger.info("receive_rout_two 成功!----{}",msg);
        } catch (Exception e) {
            logger.info("receive_rout_two 失败!----{}",msg);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }
    }


    @RabbitHandler
    @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = RabbitMQConfig.TOPIC_QUEUE_1,durable = "true"),
            exchange =@Exchange(value = RabbitMQConfig.TOPIC_EXCHANGE,durable = "true",type = "topic"),key = "topic.out.in.in")})
    public void receive_topic_one(String msg, Channel channel, Message message) throws Exception {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            logger.info("receive_topic_one 成功!----{}",msg);
        } catch (Exception e) {
            logger.info("receive_topic_one 失败!----{}",msg);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }
    }

    @RabbitHandler
    @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = RabbitMQConfig.TOPIC_QUEUE_1,durable = "true"),
            exchange =@Exchange(value = RabbitMQConfig.TOPIC_EXCHANGE,durable = "true",type = "topic"),key = "topic.in")})
    public void receive_topic_two(String msg, Channel channel, Message message) throws Exception {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            logger.info("receive_topic_two 成功!----{}",msg);
        } catch (Exception e) {
            logger.info("receive_topic_two 失败!----{}",msg);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }
    }

    @RabbitHandler
    @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = RabbitMQConfig.TOPIC_QUEUE_2,durable = "true"),
            exchange =@Exchange(value = RabbitMQConfig.TOPIC_EXCHANGE,durable = "true",type = "topic"),key = "topic.in")})
    public void receive_topic_Three(String msg, Channel channel, Message message) throws Exception {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            logger.info("receive_topic_Three 成功!----{}",msg);
        } catch (Exception e) {
            logger.info("receive_topic_Three 失败!----{}",msg);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }
    }

}
