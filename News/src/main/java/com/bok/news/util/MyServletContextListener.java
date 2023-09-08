package com.bok.news.util;

import com.bok.news.service.NewsService;
import com.bok.news.util.crawler.WebCrawler;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
public class MyServletContextListener implements ServletContextListener {
    @Resource
    private NewsService newsService;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            newsService.insertNews();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
