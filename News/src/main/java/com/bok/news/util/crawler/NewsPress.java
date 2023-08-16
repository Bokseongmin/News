package com.bok.news.util.crawler;

public enum NewsPress {
    chosun("https://www.chosun.com/arc/outboundfeeds/rss/?outputType=xml"),
    kyungHyang("http://www.khan.co.kr/rss/rssdata/total_news.xml"),
    yonHap("https://www.yonhapnewstv.co.kr/browse/feed/"),
    jTbc("https://fs.jtbc.co.kr/RSS/newsflash.xml"),
    dongA("https://rss.donga.com/total.xml");

    private final String rssUrl;

    NewsPress(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public String getRssUrl() {
        return rssUrl;
    }
}
