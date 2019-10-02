package com.test.sensitive_word.pojo;

import lombok.Data;

/**
 * @author wangjs6
 * @version 1.0
 * @Description:
 * @date: 2019/10/2 23:14
 */
@Data
public class CheckInfo {
    private String key;
    private String time;
    private String sensitive;

    public CheckInfo() {
        this.key = "null";
        this.time = "null";
        this.sensitive = "null";
    }

    @Override
    public String toString() {
        return "CheckInfo{" +
                "key='" + key + '\'' +
                ", time='" + time + '\'' +
                ", sensitive='" + sensitive + '\'' +
                '}';
    }
}
