package com.bok.news.util.crawler;

import com.bok.news.dto.NewsDto;
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

    public Map<String, Integer> news(String pressUrl) throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Date currentDate = new Date();
        String date = dateFormat.format(currentDate);

        String address = pressUrl;
        String press = NewsPress.getPressByUrl(pressUrl);
        String writer = null;
        Document data = Jsoup.connect(address).get();
        Elements items = data.select("item");
        List<String> newsList = new ArrayList<>();

        List<NewsDto> newsPost = new ArrayList<>();

        for (Element item : items) {
            NewsDto newsDto = new NewsDto();
            String url = item.select("link").text();
            String title = item.select("title").text();
            String regdate = item.select("pubDate").text();
            Document news = Jsoup.connect(url).get();
            String content = null;

            if(Objects.equals(press, NewsPress.chosun.getPress())) {
                content = item.select("description").text();
                writer = item.select("dc|creator").text();
            } else if (Objects.equals(press, NewsPress.kyungHyang.getPress())) {
                content = news.select("p.content_text.text-l").text();
                writer = item.select("author").text();
            } else if (Objects.equals(press, NewsPress.yonHap.getPress())) {
                content = news.select("div#articleBody.detail").text();
                writer = null;
            } else if (Objects.equals(press, NewsPress.jTbc.getPress())) {
                content = news.select("div.article_content").text();
                writer = news.select("dd.name").text();
            } else if (Objects.equals(press, NewsPress.dongA.getPress())) {
                content = news.select("div#article_txt.article_txt").text();
                writer = news.select("span.name").text();;
            }
            newsDto.setPost_title(title);
            newsDto.setPost_content(content);
            newsDto.setPost_writer(writer);
            newsDto.setPost_url(url);
            newsDto.setPost_press(press);
//            newsDto.setPost_regdate(newsDto.stringToLocalDateTime(regdate.replaceAll("\\.","-")));

            newsList.add(content);
            newsPost.add(newsDto);
        }
        System.out.println(newsPost);
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
            words[i] = words[i].replaceAll("(은|는|이|가|을|를|과|와|라|인|이나|에|서|에서|까지|에게|근데|그리고|그래서|)$", "");
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