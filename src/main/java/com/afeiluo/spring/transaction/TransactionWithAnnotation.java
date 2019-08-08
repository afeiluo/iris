package com.afeiluo.spring.transaction;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ben
 * @date 2019/8/8
 */
public class TransactionWithAnnotation {

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean transfer(Long fromId, Long toId, double amount) {
        //return bankDao.transfer(fromId, toId, amount);
        return true;
    }
}
