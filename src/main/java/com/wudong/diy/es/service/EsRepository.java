package com.wudong.diy.es.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.JSON;

import com.wudong.diy.es.config.EsConfig;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yanzhao
 * @since 2021/3/31
 */
@Service
@Slf4j
public class EsRepository {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private EsConfig esConfig;


    public SearchResponse queryPageSize(SearchSourceBuilder sourceBuilder) {
        log.info("EsRepository.query sourceBuilder={}", JSON.toJSONString(sourceBuilder));
        SearchResponse searchResponse = null;
        try {
            // 4.创建并设置SearchRequest对象
            SearchRequest searchRequest = new SearchRequest();
            // 设置request要搜索的索引和类型
            searchRequest.indices(esConfig.getIndexName());
            // 设置SearchSourceBuilder查询属性
            searchRequest.source(sourceBuilder);
            sourceBuilder.from(10000);
            sourceBuilder.size(100);
            // 5.查询
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es查询失败,e", e);
        }
        return searchResponse;
    }

    public SearchResponse searchAfter() {
        log.info("EsRepository.query sourceBuilder");

        SearchResponse searchResponse = null;
        try {
            // 4.创建并设置SearchRequest对象
            SearchRequest searchRequest = new SearchRequest();
            // 设置request要搜索的索引和类型
            searchRequest.indices(esConfig.getIndexName());

            SearchHit[] hits = null;
            Object uuid = null;
            do{
                SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
                sourceBuilder.size(100);
                sourceBuilder.sort("uuid", SortOrder.ASC);
                if (Objects.nonNull(uuid)){
                    sourceBuilder.searchAfter(new Object[]{uuid});
                }
                // 设置SearchSourceBuilder查询属性
                searchRequest.source(sourceBuilder);
                // 5.查询
                searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
                hits = searchResponse.getHits().getHits();
                Arrays.stream(hits).forEach(objMap->{
                    Map<String, Object> sourceAsMap = objMap.getSourceAsMap();
                    log.info("sourceAsMap:{}",JSON.toJSONString(sourceAsMap));
                });
                uuid = hits[hits.length-1].getSourceAsMap().get("uuid");
            } while (hits.length == 100);
        } catch (Exception e) {
            log.error("es查询失败,e", e);
        }
        return searchResponse;
    }


    public IndexResponse save() {
        log.info("EsRepository.save ");
        IndexResponse indexResponse = null;
        try {
            //index_name为索引名称；type_name为类型名称；doc_id为文档的id。
            IndexRequest indexRequest = new IndexRequest(esConfig.getIndexName(), esConfig.getTypeName());
            for (int i = 7;i<11000;i++){
                indexRequest.id(String.valueOf(i));
                Map<String, Object> map = new HashMap<>();
                map.put("name","兰崖"+i);
                map.put("age",27);
                map.put("desc",i+"兰崖测试啊啊啊啊啊");
                indexRequest.source(map, XContentType.JSON);
                indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            }
        } catch (Exception e) {
            log.error("es新增失败,e", e);
        }
        return indexResponse;
    }

    public IndexResponse update() {
        log.info("EsRepository.update");
        IndexResponse indexResponse = null;
        try {
            IndexRequest indexRequest = new IndexRequest(esConfig.getIndexName(), esConfig.getTypeName());
            for (int i = 1;i<11000;i++){
                indexRequest.id(String.valueOf(i));
                Map<String, Object> map = new HashMap<>();
                map.put("name","兰崖"+i);
                map.put("age",27);
                map.put("desc",i+"兰崖测试啊啊啊啊啊");
                map.put("uuid",i);
                indexRequest.source(map, XContentType.JSON);
                indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            }
            return indexResponse;
        } catch (Exception e) {
            log.error("更新es失败,e", e);
        }
        return indexResponse;
    }
}
