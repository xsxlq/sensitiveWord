package com.test.sensitive_word.dao;

import com.test.sensitive_word.pojo.SysSensitiveWord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysSensitiveWordMapper {

    List<SysSensitiveWord> selectAll();
}