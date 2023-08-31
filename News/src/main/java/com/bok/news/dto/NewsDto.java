package com.bok.news.dto;

import com.bok.news.util.crawler.NewsPress;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.datetime.DateFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

@Getter
@Setter
@ToString
public class NewsDto {
    private Long post_id;
    private String post_title;
    private String post_content;
    private String post_writer;
    private String post_url;
    private String post_press;
    private LocalDateTime post_regdate;

    public LocalDateTime stringToLocalDateTime(String press, String regdate) {
        DateTimeFormatter formatter = null;
        LocalDateTime localDateTime = null;
        if (Objects.equals(press, NewsPress.kyungHyang.getPress())) {
            formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            localDateTime = LocalDateTime.parse(regdate, formatter);
        } else if (Objects.equals(press, NewsPress.jTbc.getPress())) {
            formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            LocalDate localDate = LocalDate.parse(regdate, formatter);

            localDateTime = LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
        } else {
            formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            localDateTime = LocalDateTime.parse(regdate, formatter);
        }
        return localDateTime;
    }
}
