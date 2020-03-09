package com.test.sensitive_word.dao;

import com.test.sensitive_word.pojo.SysSensitiveWord;

import java.util.List;

public interface SysSensitiveWordMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysSensitiveWord record);

    int insertSelective(SysSensitiveWord record);

    SysSensitiveWord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysSensitiveWord record);

    int updateByPrimaryKey(SysSensitiveWord record);

    List<SysSensitiveWord> selectAll();

}