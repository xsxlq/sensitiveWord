package com.test.sensitive_word.interceptor;

import com.test.sensitive_word.pojo.SysSensitiveWord;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * 测试拦截器，对结果集修改 -- 方法一
 */
@Intercepts({@Signature(type = Executor.class,
        method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
//@Component
public class MyFirstPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MyFirstPlugin.intercept...");
        Object object = invocation.proceed();
        if(object instanceof ArrayList){
            ArrayList arrayList = (ArrayList) object;
            int i = 0;
            for (Object o : arrayList) {
                if(o instanceof SysSensitiveWord){
                    SysSensitiveWord sysSensitiveWord = (SysSensitiveWord)o;
                    sysSensitiveWord.setUpdateTime(new Date());
                    sysSensitiveWord.setId(String.valueOf(i++));
                    sysSensitiveWord.setIstatus("1");
                }
            }
        }
        return object;
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
