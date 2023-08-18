package com.bok.news.controller;

import com.bok.news.service.NewsService;
import com.bok.news.util.support.MspUtil;
import com.bok.news.util.support.result.MspResult;
import com.bok.news.util.support.result.MspStatus;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class NewsController {

    @Resource
    private NewsService newsService;

    @GetMapping("/test")
    public ResponseEntity<MspResult> home() throws Exception {
        MspResult result;
        Map<String, Integer> test = newsService.test();
        JSONObject jsonObject = new JSONObject(test);
        System.out.println(jsonObject.toString());
        if(test != null) {
            result = MspUtil.makeResult(MspStatus.OK, jsonObject);
        } else {
            result = MspUtil.makeResult("4444", "데이터가 없습니다.", jsonObject);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
