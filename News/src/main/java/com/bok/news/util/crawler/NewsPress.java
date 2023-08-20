package com.bok.news.util.crawler;

import java.util.HashMap;
import java.util.Map;

public enum NewsPress {
    chosun("조선일보", "https://www.chosun.com/arc/outboundfeeds/rss/?outputType=xml"),
    kyungHyang("경향신문", "http://www.khan.co.kr/rss/rssdata/total_news.xml"),
    yonHap("연합뉴스", "https://www.yonhapnewstv.co.kr/browse/feed/"),
    jTbc("JTBC", "https://fs.jtbc.co.kr/RSS/newsflash.xml"),
    dongA("동아일보", "https://rss.donga.com/total.xml");

    private static final Map<String, String> newsMap = new HashMap<>();

    static {
        for (NewsPress newsPress : NewsPress.values()) {
            newsMap.put(newsPress.getUrl(), newsPress.getPress());
        }
    }

    private final String url;
    private final String press;

    NewsPress(String press, String url) {
        this.press = press;
        this.url = url;
    }

    public String getPress() {
        return press;
    }

    public String getUrl() {
        return url;
    }

    public static String getPressByUrl(String url) {
        return newsMap.get(url);
    }
}
