package com.test.sensitive_word.interceptor;

import com.test.sensitive_word.pojo.SysSensitiveWord;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 测试拦截器，对结果集修改 方法二
 */
@Intercepts({@Signature(type= ResultSetHandler.class,
        method = "handleResultSets",
        args = {Statement.class})})
// @Component
public class MySecondPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        // 获取到当前的Statement
        Statement stmt =  (Statement) args[0];
        // 通过Statement获得当前结果集
        ResultSet resultSet = stmt.getResultSet();
        List<Object> resultList = new ArrayList<Object>();
        while (resultSet != null && resultSet.next()) {
            SysSensitiveWord sysSensitiveWord = new SysSensitiveWord();
            // word_name字段
            // 针对数据库查询的字段
            String wordName = resultSet.getString("word_name");
            sysSensitiveWord.setWordName(wordName);
            sysSensitiveWord.setIstatus("1");
            resultList.add(sysSensitiveWord);
        }
        System.out.println(resultList);
        return resultList;
    }

    @Override
    public Object plugin(Object o) {
        System.out.println("MyFirstPlugin.plugin...");
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("MyFirstPlugin.setProperties...");
    }
}
