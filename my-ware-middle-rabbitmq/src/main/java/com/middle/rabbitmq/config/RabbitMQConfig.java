package com.middle.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * rabbitMQ的交换机配置文件
 * @author 郑晨
 * @date 2020/4/11 11:53
 * @updateUser
 * @updateDate
 * @updateRemark
 * @version 1.0
 */
@Configuration
public class RabbitMQConfig {
    public static final String HELLO_QUEUE = "hello_queue";
    public static final String HELLO_QUEUE_1 = "hello_queue_1";
    public static final String PUB_QUEUE = "pub_queue";
    public static final String SUB_QUEUE = "sub_queue";
    public static final String ROUT_QUEUE = "rout_queue";
    public static final String TOPIC_QUEUE_1 = "topic_queue_1";
    public static final String TOPIC_QUEUE_2 = "topic_queue_2";

    public static final String DIRECT_EXCHANGE = "direct_exchange";
    public static final String FANOUT_EXCHANGE = "fanout_exchange";
    public static final String TOPIC_EXCHANGE = "topic_exchange";


    @Bean
    public Queue helloQueue() {
        return new Queue(RabbitMQConfig.HELLO_QUEUE);
    }

    @Bean
    public Queue helloQueue_1() {
        return new Queue(RabbitMQConfig.HELLO_QUEUE_1);
    }



    @Bean
    public Queue pubQueue() {
        return new Queue(RabbitMQConfig.PUB_QUEUE);
    }

    @Bean
    public Queue subQueue() {
        return new Queue(RabbitMQConfig.SUB_QUEUE);
    }


    @Bean
    public Queue routQueue () {
        return new Queue(RabbitMQConfig.ROUT_QUEUE);
    }
    @Bean
    public Queue topicQueue_1 () {
        return new Queue(RabbitMQConfig.TOPIC_QUEUE_1);
    }
    @Bean
    public Queue topicQueue_2 () {
        return new Queue(RabbitMQConfig.TOPIC_QUEUE_2);
    }



    //采用交换机 Direct
    @Bean
    public DirectExchange directExchage(){
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    //采用交换机 Fanout 广播
    @Bean
    public FanoutExchange fanoutExchage(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    //采用交换机 Fanout 广播
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }


    //绑定
    @Bean
    public Binding fanoutBingding_1(){
        return BindingBuilder.bind(pubQueue()).to(fanoutExchage());
    }
    @Bean
    public Binding fanoutBingding_2(){
        return BindingBuilder.bind(subQueue()).to(fanoutExchage());
    }

    @Bean
    public Binding directBingding_1(){
        return BindingBuilder.bind(routQueue()).to(directExchage()).with("rout_1");
    }

    @Bean
    public Binding directBingding_2(){
        return BindingBuilder.bind(routQueue()).to(directExchage()).with("rout_2");
    }

    @Bean
    public Binding topicBingding_1(){
        return BindingBuilder.bind(topicQueue_1()).to(topicExchange()).with("topic.#");
    }

    @Bean
    public Binding topicBingding_2(){
        return BindingBuilder.bind(topicQueue_2()).to(topicExchange()).with("*.msg");
    }


}
