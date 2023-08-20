package com.bok.news.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.datetime.DateFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public LocalDateTime stringToLocalDateTime(String regdate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.parse(regdate, dateTimeFormatter);
    }
}
