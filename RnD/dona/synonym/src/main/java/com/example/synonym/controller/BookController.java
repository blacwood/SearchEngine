package com.example.synonym.controller;

import com.example.synonym.source.Writer;
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

    @GetMapping("/analyze/{analyzer}/{text}/{field}")
    @ResponseBody
    public List<Map<String, Object>> synonym(@PathVariable(value = "analyzer") String analyzer, @PathVariable(value = "text") String text, @PathVariable(value = "field") String field) {
        List<Map<String, Object>> matchedValue = new ArrayList<>();
        try {
            matchedValue =  writer.synonym(analyzer, text, field);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matchedValue;
    }
}
