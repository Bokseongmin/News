package com.bok.news.util.support;

import com.bok.news.util.support.annotation.MSP;
import com.bok.news.util.support.result.MspResult;
import com.bok.news.util.support.result.MspStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

import static com.bok.news.util.support.MspUtil.makeResult;

@Slf4j
@RestControllerAdvice(annotations = {MSP.class})
public class MspHandler {

    @ExceptionHandler
    public ResponseEntity<MspResult> globalExceptionHandler(Exception e) {
        log.error("Exception occurred.", e);
        HashMap<String, String> errMsg = new HashMap<>();
        errMsg.put("error_msg", e.getMessage());
        MspResult result = makeResult(MspStatus.ERROR, errMsg);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}