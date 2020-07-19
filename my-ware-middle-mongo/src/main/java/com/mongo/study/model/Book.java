package com.mongo.study.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document("book")
public class Book implements Serializable {
    @Id
    private volatile  String id;
    private volatile  Integer price;
    private volatile  String name;
    private volatile String info;
    private volatile String publish;
    private volatile Date createTime;
    private volatile Date updateTime;

    public Book() {
    }

    public Book(String id, Integer price, String name, String info, String publish, Date createTime, Date updateTime) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.info = info;
        this.publish = publish;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
