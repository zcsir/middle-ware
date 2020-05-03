package com.middle.rabbitmq.controller;

import com.middle.rabbitmq.producer.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rabbitmq")
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(MessageController.class) ;

    @Autowired
    MessageProducer producer;

    @RequestMapping("/sendHello")
    public String sendHelloMessage(@RequestParam("message") String message){
        producer.sendHello(message);
        return "success";
    }

    @RequestMapping("/sendHello_1")
    public String sendHelloMessage_1(@RequestParam("message") String message){
        producer.sendHello_1(message);
        return "success";
    }


    @RequestMapping("/sendHello_2")
    @ResponseBody
    public String sendHelloMessage_2(@RequestParam("message") String message){
        producer.sendHello_2(message);
        return "success";
    }

    @RequestMapping("/sendHello_3")
    @ResponseBody
    public String sendHelloMessage_3(@RequestParam("message") String message){
        producer.sendHello_3(message);
        return "success";
    }

    @RequestMapping("/sendHello_4")
    @ResponseBody
    public String sendHelloMessage_4(@RequestParam("message") String message){
        producer.sendHello_4(message);
        return "success";
    }

    //topic
    @RequestMapping("/sendHello_5")
    @ResponseBody
    public String sendHelloMessage_5(@RequestParam("message") String message){
        producer.sendHello_5(message);
        return "success";
    }

    @RequestMapping("/sendHello_6")
    @ResponseBody
    public String sendHelloMessage_6(@RequestParam("message") String message){
        producer.sendHello_6(message);
        return "success";
    }
    @RequestMapping("/sendHello_8")
    @ResponseBody
    public String sendHelloMessage_8(@RequestParam("message") String message){
        producer.sendHello_8(message);
        return "success";
    }

    @RequestMapping("/sendHello_7")
    @ResponseBody
    public String sendHelloMessage_7(@RequestParam("message") String message){
        producer.sendHello_7(message);
        return "success";
    }
}