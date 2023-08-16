package com.bok.news.controller;

import com.bok.news.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
public class NewsController {

    @Resource
    private NewsService newsService;

    @GetMapping("/test")
    public String home() throws IOException {
        newsService.test();
        return null;
    }
}
