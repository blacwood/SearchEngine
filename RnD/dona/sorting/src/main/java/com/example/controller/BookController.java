package com.example.controller;

import com.example.source.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private Writer writer;

    @RequestMapping(value = "/sorting", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> sorting(@RequestBody List<String> products) {
        List<Map<String, Object>> matchedValue = new ArrayList<>();
        try {
            matchedValue =  writer.sorting(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matchedValue;
    }

    @RequestMapping(value = "/getProducts", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getProducts(@RequestBody List<String> products) {
        List<Map<String, Object>> matchedValue = new ArrayList<>();
        try {
            matchedValue =  writer.getProducts(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matchedValue;
    }
}
