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
 * 测试拦截器，对插入修改
 */
@Intercepts({@Signature(type= Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class})})
 @Component
public class My3Plugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Executor executor = (Executor)invocation.getTarget();
        String dbOpr = executor.getTransaction().getConnection().getMetaData().getUserName();
        Object[] args = invocation.getArgs();
        Object obj = args[1];
        if(obj instanceof SysSensitiveWord){
            ((SysSensitiveWord) obj).setId(System.currentTimeMillis() + "");
            ((SysSensitiveWord) obj).setDbOpr(dbOpr);
            ((SysSensitiveWord) obj).setUpdateTime(new Date());
        }
        Object list = invocation.proceed();
        System.out.println(list);
        return list;
    }

    @Override
    public Object plugin(Object o) {
        System.out.println("My3Plugin.plugin...");
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("My3Plugin.setProperties...");
    }
}
