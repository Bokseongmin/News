package com.bok.news.dao;

import com.bok.news.dto.NewsDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("NewsDao")
public class NewsDao extends CommonDao {
    public int insert(List<NewsDto> newsDaoList) {
        return getSqlSession().insert("news.insert", newsDaoList);
    }

    public List<NewsDto> getNews() {
        return getSqlSession().selectList("news.getNews");
    }
}
