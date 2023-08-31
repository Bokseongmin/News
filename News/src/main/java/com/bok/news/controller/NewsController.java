package com.bok.news.controller;

import com.bok.news.service.NewsService;
import com.bok.news.util.support.MspUtil;
import com.bok.news.util.support.annotation.MSP;
import com.bok.news.util.support.result.MspResult;
import com.bok.news.util.support.result.MspStatus;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@MSP
@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    private NewsService newsService;

    @GetMapping("/get/{type}")
    public ResponseEntity<MspResult> getNews(@PathVariable("type") String type) throws Exception {
        MspResult result;
        Map<String, Integer> test = newsService.test(type);
        JSONObject jsonObject = new JSONObject(test);
        if(test != null) {
            result = MspUtil.makeResult(MspStatus.OK, jsonObject.toString());
        } else {
            result = MspUtil.makeResult("4444", "데이터가 없습니다.", null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}