package com.test.sensitive_word.controller;

import com.sun.tools.javac.comp.Check;
import com.test.sensitive_word.pojo.CheckInfo;
import com.test.sensitive_word.pojo.SysSensitiveWord;
import com.test.sensitive_word.service.SysSensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class test {
    Map sensitiveWordsMap = null;

    List<SysSensitiveWord> sensitiveWordsList = null;

    List<String> strSensitiveWordsList = new ArrayList<>();

    @Autowired
    private SysSensitiveWordService sysSensitiveWordService;

    @PostMapping("init")
    @ResponseBody
    public String showIsNotSensitiveKeys() {
        long startTime = System.currentTimeMillis();
        sensitiveWordsList = sysSensitiveWordService.selectAll();
        for (SysSensitiveWord s:sensitiveWordsList
             ) {
            strSensitiveWordsList.add(s.getWordName());
        }
        initSensitiveMap(sensitiveWordsList);
        long endTime = System.currentTimeMillis(); // 获取结束时间
        long dfaT = endTime - startTime;
        String initInfo = "初始化时间： " + dfaT + "ms : "+";词汇数量："+sensitiveWordsList.size();
        System.out.println(initInfo);

        return initInfo;
    }

    /**
     * 初始化 敏感词map
     *
     * @return
     */
    public Map initSensitiveMap(List<SysSensitiveWord> sensitiveWordsList) {
        Set<String> initSet = new HashSet<>();

        // 一. 将数据库敏感词放到Set中
        for (SysSensitiveWord s : sensitiveWordsList
        ) {
            initSet.add(s.getWordName().trim());
        }

        // 二. 将Set中敏感词按照DFA算法放到Map中
        sensitiveWordsMap = new HashMap(initSet.size());

        //key : 每个敏感词
        String key = null;

        // 遍历时最为最新map
        Map nowMap = new HashMap();

        //存放map每个值的状态
        Map<String, String> newMap = null;

        //遍历Set
        Iterator<String> iterator = initSet.iterator();
        while (iterator.hasNext()) {
            key = iterator.next();

            nowMap = sensitiveWordsMap;

            for (int i = 0; i < key.length(); i++) {
                char k = key.charAt(i);
                Object wordMap = nowMap.get(k);

                //为空证明还没有该字的map,添加的map中，并更新当前map用于下一次遍历比较
                if (wordMap == null) {
                    newMap = new HashMap<>();
                    newMap.put("isEnd", "0");
                    nowMap.put(k, newMap);
                    nowMap = newMap;
                } else {
                    nowMap = (Map) wordMap;
                }
                if (i == key.length() - 1) {
                    nowMap.put("isEnd", "1");
                }
            }
        }
        return sensitiveWordsMap;
    }

    /**
     *
     * @return
     */
    @PostMapping("check")
    @ResponseBody
    public String checkIt(String keyWord) {
        CheckInfo checkInfo = new CheckInfo();
        if(sensitiveWordsMap == null){
            checkInfo.setSensitive("请先初始化词汇！");
            return checkInfo.toString();
        }
        if(keyWord == null){
            checkInfo.setSensitive("传值错误");
            System.out.println(keyWord);
            return checkInfo.toString();
        }
        return checkWords(keyWord.trim()).toString();
    }

    public CheckInfo checkWords(String key) {
        CheckInfo checkInfo = new CheckInfo();
        if (key == null || "".equals(key) == true) {
            checkInfo.setSensitive("传值错误");
            return checkInfo;
        }
        checkInfo.setKey(key);
        int endIndex = 0;
        long startTime = System.currentTimeMillis();
        long endTime = startTime;
        Map nowMap = sensitiveWordsMap;
        for (int i = 0; i < key.length(); i++) {
            char k = key.charAt(i);
            Object newMap = nowMap.get(k);


            if (newMap == null) {
                startTime = System.currentTimeMillis();
                checkInfo.setTime((endTime - startTime)+"ms");
                checkInfo.setSensitive("false");
                return checkInfo;
            } else {
                nowMap = (Map) newMap;
                if (nowMap.get("isEnd").equals("1")) {
                    endIndex = i;
                    startTime = System.currentTimeMillis();
                    checkInfo.setTime((endTime - startTime)+"ms");
                    checkInfo.setSensitive("true");
                    return checkInfo;
                }
            }
        }
        startTime = System.currentTimeMillis();
        checkInfo.setTime((endTime - startTime)+"ms");
        checkInfo.setSensitive("false");
        return checkInfo;
    }


    public void checkTime(){
        String key = null;
        int dfaWin = 0;
        int listWin = 0;
        int ping = 0;
        int len = strSensitiveWordsList.size();
        for(int i = 1;i<len;i++){
            System.out.println("-------------------------------");
            key = strSensitiveWordsList.get(i);
            System.out.println("检测词："+key);


            long startTime = System.currentTimeMillis();
            CheckInfo out = checkWords(key);
            long endTime = System.currentTimeMillis(); // 获取结束时间
            long dfaT = endTime - startTime;
            System.out.println("DFA运行时间： " + dfaT + "ms : "+out);

            long startTime1 = System.currentTimeMillis();
            Boolean o = strSensitiveWordsList.contains(key);
            long endTime1 = System.currentTimeMillis(); // 获取结束时间
            long listT = endTime1 - startTime1;
            System.out.println("直接获取的运行时间： " + listT + "ms : " + o);

            if(listT > dfaT){
                dfaWin ++;
            }else if(listT == dfaT){
                ping++;
            }else{
                listWin++;
            }
        }
        System.out.println("-------------------------------");
        System.out.println("dfa获胜次数："+dfaWin);
        System.out.println("直接查询获胜次数："+listWin);
        System.out.println("时间相同次数："+ping);
    }
}
