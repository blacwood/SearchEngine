package com.example.synonym.source;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
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

    public List<Map<String, Object>> synonym(String analyzer, String text, String field) throws IOException {
        AnalyzeRequest request = new AnalyzeRequest();
        request.index("synindex");
        request.text(text);
        request.analyzer(analyzer);
        restHighLevelClient.indices();
        AnalyzeResponse response = restHighLevelClient.indices().analyze(request, RequestOptions.DEFAULT);
        //response.getTokens().get(0).getTerm();
        List<String> tokens = new ArrayList<>();
        response.getTokens().forEach(token -> {
            tokens.add(token.getTerm());
        });
        SearchRequest searchRequest = new SearchRequest("synindex");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        List<Map<String, Object>> matchedValue = new ArrayList<>();
        tokens.forEach(token -> {
            sourceBuilder.query(QueryBuilders.matchQuery(field, token));
            searchRequest.source(sourceBuilder);
            try {
                restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT).getHits().forEach(value -> {
                    matchedValue.add(value.getSourceAsMap());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return matchedValue;
    }
}
