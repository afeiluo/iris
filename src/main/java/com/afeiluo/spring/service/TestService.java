package com.afeiluo.spring.service;

import com.afeiluo.spring.dao.Dao;

/**
 * Created by qiaolinfei on 2020/3/29.
 */
public class TestService {
    public Dao getDao() {
        return dao;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }

    private Dao dao;
}
