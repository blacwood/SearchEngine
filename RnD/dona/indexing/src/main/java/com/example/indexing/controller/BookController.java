package com.example.indexing.controller;

import com.example.indexing.source.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private Writer writer;

    @GetMapping("/bulkIndex")
    public void bulkInsert() {
        writer.bulkIndex();
    }



}
