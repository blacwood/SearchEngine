package com.example.indexing.source;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
public class Writer {

    private static final String INDEX_NAME = "product";
    private static final String INDEX_TYPE = "book_type";

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public void bulkIndex() {
        ObjectMapper mapper = new ObjectMapper();
        BulkRequest bulkRequest = new BulkRequest();
        try {
            //Book[] books = mapper.readValue(new File("C:/DevStudio/Installs/Elastic/books.json"), Book[].class);
            Object[] books = mapper.readValue(new File("C:/Users/dona/Documents/json files/products.json"), Object[].class);
            for (int i = 0; i < books.length; i++) {
                Object book = books[i];
                IndexRequest indexRequest = new IndexRequest(INDEX_NAME, INDEX_TYPE, String.valueOf(i)).
                        source(mapper.convertValue(book, Map.class));
                bulkRequest.add(indexRequest);
            }
            restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
