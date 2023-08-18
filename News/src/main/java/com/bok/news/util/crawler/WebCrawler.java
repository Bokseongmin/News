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

    public Map<String, Integer> news(String press) throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Date currentDate = new Date();
        String date = dateFormat.format(currentDate);

        String address = press;
        Document data = Jsoup.connect(address).get();
        Elements items = data.select("item");
        List<String> newsList = new ArrayList<>();

        for (Element item : items) {
            String link = item.select("link").text();
            Document news = Jsoup.connect(link).get();
            String content = news.select("div.article_content").text();
            newsList.add(content);
        }

        Map<String, Integer> wordCountMap = wordCnt(newsList);
        return wordCountMap;
    }

    public String[] wordSep(String content) {
        if (content == null) {
            return new String[0];
        }
        String replaceContent = content.replaceAll("[^가-힣a-zA-Z0-9- ]", "");
        String trimContent = replaceContent.trim();
        String[] words = trimContent.split(" ");

        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("(은|는|이|가|을|를|과|와|라|인|이나|에|서|에서|까지|에게)$", "");
            words[i] = words[i].toLowerCase();
        }
        return words;
    }

    public Map<String, Integer> wordCnt(List<String> newsList) {
        if (newsList == null) {
            return null;
        }

        Map<String, Integer> wordCountMap = new HashMap<>();

        for (String newsContent : newsList) {
            String[] words = wordSep(newsContent);
            for (String word : words) {
                wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
            }
        }
        return wordCountMap;
    }
}
