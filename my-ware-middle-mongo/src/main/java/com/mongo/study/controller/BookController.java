package com.mongo.study.controller;

import com.mongo.study.model.Book;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private MongoTemplate mongoTemplate;


    @RequestMapping("createCollection")
    @ResponseBody
    public Object createCollection() {
        mongoTemplate.createCollection(Book.class);
        return true;
    }


    @RequestMapping("dropCollection")
    @ResponseBody
    public Object dropCollection() {
        mongoTemplate.dropCollection(Book.class);
        return true;
    }


    @RequestMapping("batchInsert")
    @ResponseBody
    public Object batchInsert() {
        List<Book> bookList = new ArrayList<>();
        /*Book book1 = new Book("1",23,"三体","科幻小说","机械出版社",new Date(),new Date());
        Book book2 = new Book("2",36,"西游记","经典小说","毛事出版社",new Date(),new Date());
        Book book3 = new Book("3",23,"红楼梦","四大名著","北京出版社",new Date(),new Date());*/
        Book book1 = new Book("7", 23, "流浪地球", "科幻小说", "机械出版社", new Date(), new Date());
        Book book2 = new Book("8", 36, "三国演义", "经典小说", "毛事出版社", new Date(), new Date());
        Book book3 = new Book("9", 23, "三侠五义", "经典小说", "北京出版社", new Date(), new Date());
        Book book4 = new Book("10", 23, "儿女英雄传", "经典小说", "毛衫出版社", new Date(), new Date());
        Collections.addAll(bookList, book1, book2, book3,book4);
        return mongoTemplate.insertAll(bookList);
    }


    @RequestMapping("save")
    @ResponseBody
    public Object save() {
        Book book = new Book("4", 33, "水浒站", "四大名著", "北京出版社", new Date(), new Date());
        return mongoTemplate.save(book);
    }

    @RequestMapping("findAll")
    @ResponseBody
    public Object findAll() {
        return mongoTemplate.findAll(Book.class);
    }


    @RequestMapping("findById/{id}")
    @ResponseBody
    public Object find(@PathVariable String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.find(query, Book.class);
    }


    @RequestMapping("update")
    @ResponseBody
    public Object find(Book book) {
        Query query = new Query(Criteria.where("_id").is(book.getId()));
        Update update = new Update()
                .set("name", book.getName())
                .set("price", book.getPrice())
                .set("publish", book.getPublish());
        return mongoTemplate.updateMulti(query, update, Book.class);
    }


    @RequestMapping("delete/{id}")
    @ResponseBody
    public Object delete(@PathVariable String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.remove(query, Book.class);
    }


    /**
     * 按照书的类型分组，并计算平均值和书的总量，计算所有书的总量，并取出最大值的书进行展示
     *
     * @param id
     * @return
     */
    @RequestMapping("bookDetailList")
    @ResponseBody
    public Object bookDetailList() {
        Criteria criteria =   Criteria.where("publish").ne("毛衫出版社");

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.project("price", "name", "info", "publish", "createTime", "updateTime"),
                Aggregation.group("info")
                        .first("info").as("info")
                        .max("price").as("max_book")
                        .avg("price").as("avg_book")
                        .sum("price").as("sum_book")
                        .count().as("count_book")
                //Aggregation.sort(new Sort(new Sort.Order(Sort.Direction.DESC, "totalPayment"))),
                //Aggregation.skip(0),
                //Aggregation.limit(10)

        );
        List<Object> list = new ArrayList<>();
        AggregationResults<BasicDBObject> customerList = mongoTemplate.aggregate(aggregation,"book",BasicDBObject.class);
        for (Iterator<BasicDBObject> iterator = customerList.iterator(); iterator.hasNext(); ) {
            DBObject obj = iterator.next();
            list.add(obj);
        }
        return list;
    }




















}
