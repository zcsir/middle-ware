package com.elastic.search.controller;

import com.alibaba.fastjson.JSONArray;
import com.elastic.search.model.Item;
import com.elastic.search.repository.ItemRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping("/createIndex")
    @ResponseBody
    public boolean createIndex() {
        return elasticsearchTemplate.createIndex(Item.class);
    }


    @RequestMapping("/putMapping")
    @ResponseBody
    public Object putMapping() {
        elasticsearchTemplate.putMapping(Item.class);
        return elasticsearchTemplate.getMapping(Item.class);
    }

    @RequestMapping("/createData")
    @ResponseBody
    public List<Item> createData() {
        List<Item> items = new ArrayList<>();
        Item item1 = new Item((long) 1, "排球", "体育用品", "361", (long) 231.6, "361°集团是一家集品牌、研发、设计、生产、经销为一体的综合性体育用品公司，其产品包括运动鞋、服及相关配件、童装、时尚休闲等多品类，集团成立于2003 年，在致力于成为全球令人尊敬的品牌典范精神引领下，已经成为中国领先的运动品牌企业之一。品牌于2009年6月30日，于香港联交所主板成功上市，股份代码为01361·HK，作为中国发展速度迅猛的体育用品品牌，在中国传统行业中，从品牌成立到上市仅用了6年时间,创造了业内佳话。");
        Item item2 = new Item((long) 2, "牙刷 牙膏 剃须刀", "生活用品", "飞利浦", (long) 132, "飞利浦，1891年成立于荷兰，主要生产照明、家庭电器、医疗系统方面的产品。飞利浦公司，2007年全球员工已达128,100人，在全球28个国家有生产基地，在150个国家设有销售机构，拥有8万项专利，实力超群。2011年7月11日，飞利浦宣布收购奔腾电器（上海）有限公司，金额约25亿元。");
        Item item3 = new Item((long) 3, "眼镜 眼药水眼睫毛", "生活用品", "莎普爱思", (long) 15, "保护眼睛，人人有责，一家好的品牌");
        Item item4 = new Item((long) 4, "华为手机", "电子用品", "华为", (long) 3999, "华为手机分为华为和荣耀两个品牌");
        Item item5 = new Item((long) 5, "ipad 10 ", "电子用品", "苹果", (long) 2399, "比华为手机大的一种产品");
        Item item6 = new Item((long) 6, "键盘 鼠标", "电子用品", "雷蛇", (long) 231, "机械键盘的手感很好");
        Item item7 = new Item((long) 7, "桌子 椅子", "家具", "JI", (long) 2399, "1.5米的桌子和1米的凳子");
        Item item8 = new Item((long) 8, "电脑 笔记本电脑 华为电脑", "电子用品", "华为", (long) 7999, "华为笔记本已经上市");
        Collections.addAll(items, item1, item2, item3, item4, item5, item6, item7, item8);
        itemRepository.saveAll(items);
        return elasticsearchTemplate
                .queryForList(new NativeSearchQueryBuilder().
                        withQuery(matchAllQuery()).build(), Item.class);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Item> findAll() {
        return elasticsearchTemplate
                .queryForList(new NativeSearchQueryBuilder().
                        withQuery(matchAllQuery()).build(), Item.class);
    }

    @RequestMapping("/queryString/{word}")
    @ResponseBody
    public Object queryString(@PathVariable("word") String word) {
        return elasticsearchTemplate
                .queryForList(new NativeSearchQueryBuilder()
                        .withQuery(QueryBuilders.queryStringQuery(word))
                        .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                        .build(), Item.class);
    }

    /**
     * 按照分类进行分组，并查询分类（category）下数量
     *
     * @return
     */
    @RequestMapping("/count/category")
    @ResponseBody
    public Object count_category() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("item").withTypes("_doc")
                .addAggregation(AggregationBuilders.terms("categorys").field("category"))
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        //Aggregation aggregation = query.get("categorys");
        // 获取聚合结果
        StringTerms teamAgg = (StringTerms) aggregations.asMap().get("categorys");
        List<StringTerms.Bucket> bucketList = teamAgg.getBuckets();
        List<Map<String, Object>> maps = new ArrayList<>();
        for (StringTerms.Bucket bucket : bucketList) {
            Map map = new HashMap<String, Object>(2);
            map.put(bucket.getKeyAsString(), bucket.getDocCount());
            maps.add(map);
        }
        return maps;

    }

    /**
     * 计算分类下的所有商品的平均价格
     *
     * @return
     */
    @RequestMapping("/group_category/avg_price")
    @ResponseBody
    public Object category_avg_price() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("item").withTypes("_doc")
                .addAggregation(AggregationBuilders.terms("group_category").field("category")
                        //分组后进行平均值的查询
                        .subAggregation(AggregationBuilders.avg("avg_price").field("price")))
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });
        StringTerms groupCategory = (StringTerms)aggregations.asMap().get("group_category");
        List<Map<String,Object>> lists = new ArrayList<>();
        groupCategory.getBuckets().forEach(bucket -> {
            Map<String,Object> map = new HashMap<>(3);
            String key = bucket.getKeyAsString();
            long docCount = bucket.getDocCount();
            //获取当前分组下一级的平均分信息
            InternalAvg avg = (InternalAvg)bucket.getAggregations().asMap().get("avg_price");
            map.put("category",key);
            map.put("count",docCount);
            map.put("avg",avg.getValue());
            lists.add(map);
        });
        return lists;
    }


    /**
     * 单个item索引类型进行分页
     *
     * @param pageable
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    public Object page(@PageableDefault Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withPageable(pageable)
                .build();
        //分页
        AggregatedPage<Item> items = elasticsearchTemplate.queryForPage(searchQuery, Item.class);

        return items;

    }

    /**
     * 多个索引类型进行分页
     *
     * @param pageable
     * @return
     */
    @RequestMapping("/page_more")
    @ResponseBody
    public Object page_more(String word, @PageableDefault Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                //多索引
                .withIndices("item", "student")
                //查询条件
                .withQuery(queryStringQuery(word))
                //分页
                .withPageable(pageable)
                .build();
        //分页
        List<Map> result = elasticsearchTemplate.query(searchQuery, response -> {
            SearchHits hits = response.getHits();
            List<Map> list = new ArrayList<>();
            Arrays.stream(hits.getHits()).forEach(h -> {
                Map<String, Object> source = h.getSourceAsMap();
                System.out.println(JSONArray.toJSONString(source));
                list.add(source);
            });
            return list;
        });
        return result;
    }


}
