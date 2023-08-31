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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class WebCrawler {

    public Map<String, Integer> news(String pressUrl, String type) throws Exception {
        int threads = NewsPress.values().length;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Date currentDate = new Date();
        String date = dateFormat.format(currentDate);

        String address = pressUrl;
        String press = NewsPress.getPressByUrl(pressUrl);

        Document data = Jsoup.connect(address).get();
        Elements items = data.select("item");

        List<String> newsList = Collections.synchronizedList(new ArrayList<>());
        List<NewsDto> newsPost = Collections.synchronizedList(new ArrayList<>());

        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        for (Element item : items) {
            executorService.submit(() -> {
                processNewsItem(item, press, newsList, newsPost, type);
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        Map<String, Integer> wordCountMap = wordCnt(newsList);
        return wordCountMap;
    }

    private void processNewsItem(Element item, String press, List<String> newsList, List<NewsDto> newsPost, String type) {
        NewsDto newsDto = new NewsDto();
        String url = item.select("link").text();
        String title = item.select("title").text();
        String regdate = item.select("pubDate").text();
        String writer = null;

        try {
            Document news = Jsoup.connect(url).get();
            String content = "";

            if (Objects.equals(press, NewsPress.chosun.getPress())) {
                content = item.select("description").text();
                writer = item.select("dc|creator").text();
            } else if (Objects.equals(press, NewsPress.kyungHyang.getPress())) {
                content = news.select("p.content_text.text-l").text();
                writer = item.select("author").text();
                regdate = item.select("dc|date").text();
            }

            newsDto.setPost_title(title);
            newsDto.setPost_content(content);
            newsDto.setPost_writer(writer);
            newsDto.setPost_url(url);
            newsDto.setPost_press(press);
            newsDto.setPost_regdate(newsDto.stringToLocalDateTime(press, regdate));

            synchronized (newsList) {
                if (Objects.equals(type, "title")) {
                    newsList.add(title);
                } else {
                    newsList.add(content);
                }
            }
            newsPost.add(newsDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] wordSep(String content) {
        if (content == null) {
            return new String[0];
        }
        String replaceContent = content.replaceAll("[^가-힣a-zA-Z0-9- ]", "");
        String trimContent = replaceContent.trim();
        String[] words = trimContent.split(" ");

        for (int i = 0; i < words.length; i++) {
            words[i] = words[i]
                    .replaceAll("(은|는|이|가|을|를|과|와|라|인|이나|에|서|에서|까지|에게|근데|그리고|그래서|수|있다|구독|것|그|있|)$", "");
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

        wordCountMap.entrySet().removeIf(entry -> entry.getValue() == 1);
        return wordCountMap;
    }

    public Map<String, Integer> getTop10(Map<String, Integer> data) {
        Map<String, Integer> top10 = new LinkedHashMap<>();

        List<Map.Entry<String, Integer>> stored = data.entrySet().stream().sorted(Collections
                .reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toList());

        for(int i = 0; i < Math.min(10, stored.size()); i++) {
            Map.Entry<String, Integer> entry = stored.get(i);
            top10.put(entry.getKey(), entry.getValue());
        }

        return top10;
    }
}