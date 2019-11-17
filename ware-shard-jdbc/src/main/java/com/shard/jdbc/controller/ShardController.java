package com.shard.jdbc.controller;

import com.shard.jdbc.entity.TableOne;
import com.shard.jdbc.entity.TableTwo;
import com.shard.jdbc.service.ShardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
public class ShardController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private ShardService shardService ;
    /**
     * 1、建表流程
     */
    @RequestMapping("/createTable")
    public String createTable (){
        shardService.createTable();
        return "success" ;
    }
    /**
     * 2、生成表 table_one 数据
     */
    @RequestMapping("/insertOne")
    public String insertOne (){
        shardService.insertOne();
        return "SUCCESS" ;
    }
    /**
     * 3、生成表 table_two 数据
     */
    @RequestMapping("/insertTwo")
    public String insertTwo (){
        shardService.insertTwo();
        return "SUCCESS" ;
    }
    /**
     * 4、查询表 table_one 数据
     */
    @RequestMapping("/selectOneByPhone/{phone}")
    public TableOne selectOneByPhone (@PathVariable("phone") String phone){
        return shardService.selectOneByPhone(phone);
    }
    /**
     * 5、查询表 table_one 数据
     */
    @RequestMapping("/selectTwoByPhone/{phone}")
    public TableTwo selectTwoByPhone (@PathVariable("phone") String phone){
        return shardService.selectTwoByPhone(phone);
    }

    @RequestMapping("/setKey/{key}/{value}")
    public void selectTwoByPhone (@PathVariable("key") String key,@PathVariable("value") String value){
        redisTemplate.opsForValue().set(key,value);
    }
}
