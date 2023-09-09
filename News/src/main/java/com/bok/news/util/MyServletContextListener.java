package com.bok.news.util;

import com.bok.news.service.NewsService;
import com.bok.news.util.crawler.WebCrawler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
@Component
public class MyServletContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private NewsService newsService;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        try {
            int affectRow = newsService.insertNews();
            if(affectRow > 0) {
                log.info("데이터가 성공적으로 저장되었습니다.");
            } else {
                log.error("데이터 저장중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
