package com.elastic.search.service.impl;

import com.elastic.search.model.RequestLog;
import com.elastic.search.repository.RequestLogRepository;
import com.elastic.search.service.RequestLogService;
import com.elastic.search.util.DateUtil;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.Scroll;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RequestLogServiceImpl implements RequestLogService {

    @Resource
    private RequestLogRepository requestLogRepository ;

    @Override
    public String esInsert(Integer num) {
        for (int i = 0 ; i < num ; i++){
            RequestLog requestLog = new RequestLog() ;
            requestLog.setId(System.currentTimeMillis());
            requestLog.setOrderNo(DateUtil.formatDate(new Date(),DateUtil.DATE_FORMAT_02)+System.currentTimeMillis());
            requestLog.setUserId("userId"+i);
            requestLog.setUserName("张三"+i);
            requestLog.setCreateTime(DateUtil.formatDate(new Date(),DateUtil.DATE_FORMAT_01));
            requestLogRepository.save(requestLog) ;
        }
        return "success" ;
    }

    @Override
    public Iterable<RequestLog> esFindAll (){
        return requestLogRepository.findAll() ;
    }

    @Override
    public String esUpdateById(RequestLog requestLog) {
        requestLogRepository.save(requestLog);
        return "success" ;
    }

    @Override
    public Optional<RequestLog> esSelectById(Long id) {
        return requestLogRepository.findById(id) ;
    }

    @Override
    public Iterable<RequestLog> esFindOrder() {
        // 用户名倒序
        // Sort sort = new Sort(Sort.Direction.DESC,"userName.keyword") ;
        // 创建时间正序
        Sort sort = new Sort(Sort.Direction.ASC,"createTime.keyword") ;
        return requestLogRepository.findAll(sort) ;
    }

    @Override
    public Iterable<RequestLog> esFindOrders() {
        List<Sort.Order> sortList = new ArrayList<>() ;
        Sort.Order sort1 = new Sort.Order(Sort.Direction.ASC,"createTime.keyword") ;
        Sort.Order sort2 = new Sort.Order(Sort.Direction.DESC,"userName.keyword") ;
        sortList.add(sort1) ;
        sortList.add(sort2) ;
        Sort orders = Sort.by(sortList) ;
        return requestLogRepository.findAll(orders) ;
    }

    @Override
    public Iterable<RequestLog> search() {
        // 全文搜索关键字
        /*
        String queryString="张三";
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
        requestLogRepository.search(builder) ;
        */

        /*
         * 多条件查询
         */
         QueryBuilder builder = QueryBuilders.boolQuery()
                // .must(QueryBuilders.matchQuery("userName.keyword", "历张")) 搜索不到
                .must(QueryBuilders.matchQuery("userName", "张三")) // 可以搜索
                .must(QueryBuilders.matchQuery("orderNo", "201911141573739465653"));
        return requestLogRepository.search(builder) ;
    }


    /*public List<String> scroll(long lastTime,long nowTime,List<String> list){
        //设定滚动时间间隔,60秒,不是处理查询结果的所有文档的所需时间
        //游标查询的过期时间会在每次做查询的时候刷新，所以这个时间只需要足够处理当前批的结果就可以了
        final Scroll scroll = new Scroll(TimeValue.timeValueSeconds(60));
        SearchRequest searchRequest = new SearchRequest(INDEX);
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                //查询条件在 两个日期范围
                .must(QueryBuilders.rangeQuery("intercept_time").gte(lastTime).lte(nowTime));
        //每个批次实际返回的数量
        searchSourceBuilder.size(10000);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("获取数据错误1 ->", e);
        }
        assert searchResponse != null;
        String scrollId;
        do {
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                //获取需要数据
                list.add(String.valueOf(sourceAsMap.get("xxx"));
            }
            //每次循环完后取得scrollId,用于记录下次将从这个游标开始取数
            scrollId = searchResponse.getScrollId();
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            try {
                //进行下次查询
                searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                logger.error("获取数据错误2 ->", e);
            }
        } while (searchResponse.getHits().getHits().length != 0);
        //清除滚屏
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        //也可以选择setScrollIds()将多个scrollId一起使用
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = null;
        try {
            clearScrollResponse = client.clearScroll(clearScrollRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.warn("清除滚屏错误 ->", e);
        }
        boolean succeeded = false;
        if (clearScrollResponse!=null){
            succeeded = clearScrollResponse.isSucceeded();
        }

    }*/


    }
