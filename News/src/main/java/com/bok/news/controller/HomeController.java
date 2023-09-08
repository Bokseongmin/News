package com.bok.news.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.bok.news.dto.NewsDto;
import com.bok.news.service.NewsService;
import com.bok.news.util.crawler.WebCrawler;
import com.bok.news.util.support.MspUtil;
import com.bok.news.util.support.result.MspResult;
import com.bok.news.util.support.result.MspStatus;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Handles requests for the application home page.
 */
@Controller
@Slf4j
public class HomeController {
	@Resource
	private NewsService newsService;
	private final WebCrawler webCrawler = new WebCrawler();
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws Exception {
		MspResult mspResult;
		int affectRow = newsService.insertNews();
		if(affectRow > 0) {
			mspResult = MspUtil.makeResult(MspStatus.OK, null);
		} else {
			mspResult = MspUtil.makeResult("4444", "데이터를 저장하던 중 오류가 발생했습니다.", null);
		}
		log.info(mspResult.toString());
		return "home";
	}

}
