package com.bok.news.service;

import com.bok.news.util.crawler.NewsPress;
import com.bok.news.util.crawler.WebCrawler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NewsService {

    private final WebCrawler webCrawler = new WebCrawler();

    public String test() throws IOException {
        return webCrawler.news(NewsPress.jTbc.getRssUrl());
    }
}
