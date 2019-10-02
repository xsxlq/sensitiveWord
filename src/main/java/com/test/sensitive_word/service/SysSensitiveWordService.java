package com.test.sensitive_word.service;

import com.test.sensitive_word.pojo.SysSensitiveWord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SysSensitiveWordService")
public interface SysSensitiveWordService {
    List<SysSensitiveWord> selectAll();
}
