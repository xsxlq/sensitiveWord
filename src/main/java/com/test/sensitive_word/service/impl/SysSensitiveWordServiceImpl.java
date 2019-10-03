package com.test.sensitive_word.service.impl;

import com.test.sensitive_word.dao.SysSensitiveWordMapper;
import com.test.sensitive_word.pojo.SysSensitiveWord;
import com.test.sensitive_word.service.SysSensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("SysSensitiveWordService")
public class SysSensitiveWordServiceImpl implements SysSensitiveWordService {

    @Autowired
    private SysSensitiveWordMapper sysSensitiveWordMapper;
    @Override
    public List<SysSensitiveWord> selectAll() {
        return sysSensitiveWordMapper.selectAll();
    }
}
