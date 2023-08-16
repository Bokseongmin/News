package com.bok.news.util.crawler;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class WebCrawler {

    public String news(String press) throws IOException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.mm.dd", Locale.KOREA);
        Date currentDate = new Date();

        String dateTime = dateFormat.format(currentDate);
        String date = dateTime;

        String address = press;
        Document data = Jsoup.connect(address).get();
        Elements test = data.select("item");

        for(Element a : test) {
            String link = a.select("link").text();
            Document news = Jsoup.connect(link).get();
            String content = news.select("div.article_content").text();
            System.out.println(content);
        }
        return null;
    }

    public Map<String, Integer> wordCnt(List<String> newsList) throws Exception {
        if (newsList == null) {
            return null;
        }

        Map<String, Integer> wMap = new HashMap<>();

        
    }
    
}
