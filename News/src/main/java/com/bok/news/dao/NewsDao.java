package com.bok.news.dao;

import org.springframework.stereotype.Repository;

@Repository("NewsDao")
public class NewsDao extends CommonDao {
    public int insetNews() {
        return getSqlSession().insert("");
    }
}
