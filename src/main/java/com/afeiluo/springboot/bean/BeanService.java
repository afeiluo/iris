package com.afeiluo.springboot.bean;

import org.springframework.stereotype.Service;

/**
 * Created by qiaolinfei on 2020/7/12.
 */
@Service
public class BeanService {
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String desc;
}
