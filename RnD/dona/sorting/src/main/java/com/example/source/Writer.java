package com.example.source;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class Writer {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public List<Map<String, Object>> sorting(List<String> products) throws IOException {
        SearchRequest searchRequest = new SearchRequest("product3");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        List<Map<String, Object>> matchedValue = new ArrayList<>();
        sourceBuilder.query(QueryBuilders.termsQuery("_ID.keyword", products));
        sourceBuilder.sort("_ID.keyword", SortOrder.ASC);
        searchRequest.source(sourceBuilder);
        restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT).getHits().forEach(value -> {
            matchedValue.add(value.getSourceAsMap());
        });
        return matchedValue;
    }

    public List<Map<String, Object>> getProducts(List<String> products) throws IOException {
        SearchRequest searchRequest = new SearchRequest("product3");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        List<Map<String, Object>> matchedValue = new ArrayList<>();
        sourceBuilder.query(QueryBuilders.termsQuery("_ID.keyword", products));
        searchRequest.source(sourceBuilder);
        restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT).getHits().forEach(value -> {
            matchedValue.add(value.getSourceAsMap());
        });
        return matchedValue;
    }
}
