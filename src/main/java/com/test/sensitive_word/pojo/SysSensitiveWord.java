package com.test.sensitive_word.pojo;

import java.util.Date;

public class SysSensitiveWord {
    private String id;

    private String wordName;

    private Date updateTime;

    private String istatus;

    private String delFlag;

    private String dbOpr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName == null ? null : wordName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIstatus() {
        return istatus;
    }

    public void setIstatus(String istatus) {
        this.istatus = istatus == null ? null : istatus.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public String getDbOpr() {
        return dbOpr;
    }

    public void setDbOpr(String dbOpr) {
        this.dbOpr = dbOpr == null ? null : dbOpr.trim();
    }
}