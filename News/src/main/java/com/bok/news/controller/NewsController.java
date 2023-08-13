package com.bok.news.controller;

import com.bok.news.service.NewsService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class NewsController {

    @Resource
    private NewsService newsService;
}
