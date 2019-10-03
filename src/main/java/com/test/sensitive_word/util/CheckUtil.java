package com.test.sensitive_word.util;

import com.test.sensitive_word.pojo.CheckInfo;
import com.test.sensitive_word.pojo.SysSensitiveWord;
import com.test.sensitive_word.service.impl.SysSensitiveWordServiceImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wangjs6
 * @version 1.0
 * @Description:
 * @date: 2019/10/3 11:35
 */
public class CheckUtil {

    // 存放库中敏感词map
    static Map sensitiveWordsMap = null;

    /**
     * 调用初始化敏感词，并返回敏感词数量
     * @param sysSensitiveWordService
     * @return
     */
    public static int init(SysSensitiveWordServiceImpl sysSensitiveWordService){
        List<SysSensitiveWord> sensitiveWordsList = sysSensitiveWordService.selectAll();
        int wordNum = sensitiveWordsList.size();
        initSensitiveMap(sensitiveWordsList);
        return wordNum;
    }

    /**
     * 初始化 敏感词map
     *
     * @return
     */
    public static Map initSensitiveMap(List<SysSensitiveWord> sensitiveWordsList) {
        Set<String> initSet = new HashSet<>();

        // 一. 将数据库敏感词放到Set中
        sensitiveWordsList.forEach(s->{
            if(s.getWordName() != null){
                initSet.add(s.getWordName().trim());
            }
        });


        // 二. 将Set中敏感词按照DFA算法放到Map中
        sensitiveWordsMap = new HashMap(initSet.size());

        //key : 每个敏感词
        String key = null;

        // 遍历时最新map
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

    public static CheckInfo checkWords(String key) {
        CheckInfo checkInfo = new CheckInfo();
        if(sensitiveWordsMap == null){
            checkInfo.setSensitive("请先初始化库词汇！");
            return checkInfo;
        }
        if (key == null || "".equals(key)) {
            checkInfo.setSensitive("传值错误");
            return checkInfo;
        }
        key = key.trim();
        checkInfo.setKey(key);
        long startTime = System.currentTimeMillis();
        Map nowMap = sensitiveWordsMap;
        for (int i = 0; i < key.length(); i++) {
            char k = key.charAt(i);
            Object newMap = nowMap.get(k);
            if (newMap == null) {
                return updateInfo(startTime,checkInfo,"false");
            } else {
                nowMap = (Map) newMap;
                if (nowMap.get("isEnd").equals("1")) {
                    return updateInfo(startTime,checkInfo,"true");
                }
            }
        }
        return updateInfo(startTime,checkInfo,"false");
    }

    private static CheckInfo updateInfo(Long startTime,CheckInfo checkInfo,String status){
        checkInfo.setTime((System.currentTimeMillis() - startTime)+"ms");
        checkInfo.setSensitive(status);
        return checkInfo;
    }
}
