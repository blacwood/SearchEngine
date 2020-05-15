package com.example.stemmer.source;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tartarus.snowball.ext.PorterStemmer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class Writer {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public List<Map<String, Object>> stemmerData(String field, String data) throws IOException {
        SearchRequest searchRequest = new SearchRequest("product3");
        PorterStemmer stem = new PorterStemmer();
        stem.setCurrent(data);
        stem.stem();
        String result = stem.getCurrent();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery(field, result));
        searchRequest.source(sourceBuilder);
        List<Map<String, Object>> matchedValue = new ArrayList<>();
        restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT).getHits().forEach(value -> {
            matchedValue.add(value.getSourceAsMap());
        });
        return matchedValue;
    }
}
