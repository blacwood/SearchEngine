package com.example.stemmer.controller;

import com.example.stemmer.source.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private Writer writer;

    @GetMapping("/stemmer/{field}/{data}")
    @ResponseBody
    public List<Map<String, Object>> stemmer(@PathVariable(value = "field") String field, @PathVariable(value = "data") String data) {
        List<Map<String, Object>> matchedValue = new ArrayList<>();
        try {
            matchedValue =  writer.stemmerData(field, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matchedValue;
    }
}
