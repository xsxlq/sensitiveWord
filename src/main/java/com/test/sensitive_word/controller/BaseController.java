package com.test.sensitive_word.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 默认首页
     * @param model
     * @return
     */
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
    /**
     * 普通跳转
     */
    @RequestMapping("{page}.html")
    public String Page(@PathVariable String page){
        logger.debug("BaseController："+page);
        return "market/"+page;
    }
    @RequestMapping("{url}/{page}.html")
    public String Page(@PathVariable String url, @PathVariable String page){
        logger.debug("BaseController："+page);
        return "market/"+url+"/"+page;
    }
    @RequestMapping("{root}/{url}/{page}.html")
    public String Page(@PathVariable String root, @PathVariable String url, @PathVariable String page, Model model){
        logger.debug("BaseController："+page);
        return "market/"+root+"/"+url+"/"+page;
    }
    @RequestMapping("/logout")
    public String logout(Model model){
        logger.debug("BaseController：登出");
        return "market/index";
    }
}
