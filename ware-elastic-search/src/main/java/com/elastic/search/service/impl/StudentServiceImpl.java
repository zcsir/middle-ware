package com.elastic.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.elastic.search.model.StudentModel;
import com.elastic.search.service.StudentService;
import com.elastic.search.util.DateUtil;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public List<StudentModel> findAll() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery()).build();
/*
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("id","13")).build();
*/
        List<StudentModel> studentModels = elasticsearchTemplate.queryForList(searchQuery, StudentModel.class);
        return studentModels;
    }

    @Override
    public boolean batchAdd() {
        List<IndexQuery> indexQueries = new ArrayList<>();
        IndexQuery indexQuery = new IndexQuery();
        StudentModel studentModel = null;
        studentModel = new StudentModel
                ("11", "小wang", 45, "2009-05-23", "游泳 dancer", "北京", "北京大学", "暂无");
        indexQuery.setId(studentModel.getId());
        indexQuery.setIndexName("student");
        indexQuery.setType("_doc");
        indexQuery.setObject(studentModel);
        indexQueries.add(indexQuery);
        elasticsearchTemplate.bulkIndex(indexQueries);
        return true;
    }

    @Override
    public String delete(String id) {
        return elasticsearchTemplate.delete(StudentModel.class, id);
    }

    @Override
    public Object update() {
        UpdateQuery updateQuery = new UpdateQuery();
        StudentModel studentModel = new StudentModel( "南京", "清华大学", "你猜");
        studentModel.setId("11");
        updateQuery.setId(studentModel.getId());
        updateQuery.setClazz(StudentModel.class);
        UpdateRequest request = new UpdateRequest();
        request.doc(JSON.toJSONString(studentModel), XContentType.JSON);
        updateQuery.setUpdateRequest(request);
        return elasticsearchTemplate.update(updateQuery);
    }

    @Override
    public boolean add() {
        IndexQuery indexQuery = new IndexQuery();
        StudentModel studentModel = null;
        studentModel = new StudentModel
                ("12", "小san", 45, "2009-05-23", "游泳 dancer", "北京", "北京大学", "暂无");
        indexQuery.setId(studentModel.getId());
        indexQuery.setIndexName("student");
        indexQuery.setType("_doc");
        indexQuery.setObject(studentModel);
        elasticsearchTemplate.index(indexQuery);
        return true;
    }

    @Override
    public Object singleWord(String word, Pageable pageable) {
        //使用queryStringQuery完成单字符串查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryStringQuery(word)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, StudentModel.class);
    }

    @Override
    public Object singleMatch(String key, String value, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery(key,value)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, StudentModel.class);
    }

    @Override
    public Object singlePhraseMatch(String word, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchPhraseQuery("content",word).slop(2)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, StudentModel.class);
    }

    /**
     * 组合查询
     * must(QueryBuilders) :   AND
     * mustNot(QueryBuilders): NOT
     * should:                  : OR
     */
    @Override
    public Object singleTerm(String id, String interest, Pageable pageable) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery()
                .must(termQuery("id", id))
                .must(matchQuery("interest", interest));
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(builder).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, StudentModel.class);
    }

    @Override
    public Object multiMatch(String content, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(content,"content","address")).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, StudentModel.class);
    }

    @Override
    public Object contain(String content, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("content",content).operator(Operator.AND)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, StudentModel.class);
    }

    @Override
    public Object containPercent(String content, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("content", content).operator(Operator.OR).minimumShouldMatch("75%"))
                .withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, StudentModel.class);
    }


}
