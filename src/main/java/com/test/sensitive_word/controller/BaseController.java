package com.test.sensitive_word.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/")
    public String index(Model model){
        logger.debug("BaseController：/");
        return "index";
    }

    @RequestMapping("/index.html")
    public String index2(Model model){
        logger.debug("BaseController：/index");
        return "index";
    }
}
