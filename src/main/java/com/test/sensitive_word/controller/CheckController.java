package com.test.sensitive_word.controller;

import com.test.sensitive_word.pojo.CheckInfo;
import com.test.sensitive_word.pojo.SysSensitiveWord;
import com.test.sensitive_word.service.impl.SysSensitiveWordServiceImpl;
import com.test.sensitive_word.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CheckController {


    @Autowired
    private SysSensitiveWordServiceImpl sysSensitiveWordService;

    @PostMapping("init")
    @ResponseBody
    public String showIsNotSensitiveKeys() {
        long startTime = System.currentTimeMillis();
        int count = CheckUtil.init(sysSensitiveWordService);
        SysSensitiveWord sysSensitiveWord = new SysSensitiveWord();
        sysSensitiveWord.setDbOpr("test");
        sysSensitiveWordService.insertSelective(sysSensitiveWord);
        long endTime = System.currentTimeMillis();
        long dfaT = endTime - startTime;
        return "初始化时间： " + dfaT + "ms : "+";词汇数量："+count;
    }

    @PostMapping("check")
    @ResponseBody
    public CheckInfo checkIt(String keyWord) {
        return CheckUtil.checkWords(keyWord);
    }
}
