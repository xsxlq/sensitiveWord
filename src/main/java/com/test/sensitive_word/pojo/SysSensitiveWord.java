package com.test.sensitive_word.pojo;


import lombok.Data;

import java.util.Date;
@Data
public class SysSensitiveWord {
    private String id;

    private String wordName;

    private Date updateTime;

    private String istatus;

    private String delFlag;
}