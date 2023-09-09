package com.bok.news.service;

import com.bok.news.dao.NewsDao;
import com.bok.news.dto.NewsDto;
import com.bok.news.util.crawler.NewsPress;
import com.bok.news.util.crawler.WebCrawler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class NewsService {

    @Resource
    private NewsDao newsDao;

    private final WebCrawler webCrawler = new WebCrawler();

    public int insertNews() throws Exception {
        int affectRow = 0;
        List<NewsDto> newsDtoList;
        try {
            for(NewsPress press : NewsPress.values()) {
                newsDtoList = webCrawler.news(press.getUrl());
                for (NewsDto dto : newsDtoList) {
                    // MyBatis를 통해 데이터베이스에 저장하기 전에 개별적으로 저장
                    int result = newsDao.insert(dto);
                    if (result > 0) {
                        affectRow += result;
                    } else {
                        log.error(press + " insert error");
                        // 실패한 경우 처리할 내용 추가
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return affectRow;
    }

    public Map<String, Integer> getCloud(String type) {
        List<String> wordMap = new ArrayList<>();
        List<NewsDto> newsList = newsDao.getNews();
        String title = null;
        String content = null;
        if(Objects.equals(type, "title")) {
            wordMap.add(title);
        } else if (Objects.equals(type, "content")){
            wordMap.add(content);
        }

        Map<String, Integer> words = webCrawler.wordCnt(wordMap);
        Map<String, Integer> top10 = webCrawler.getTop10(words);
        return top10;
    }
}
