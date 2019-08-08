package com.afeiluo.spring.transaction;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author ben
 * @date 2019/8/8
 */
public class ApiTransactionWithTemplate {
    //private BankDao bankDao;

    private TransactionTemplate transactionTemplate;

    public boolean transfer(final Long fromId, final Long toId, final double amount) {
        return (Boolean) transactionTemplate.execute(new TransactionCallback() {
            public Object doInTransaction(TransactionStatus status) {
                Object result = true;
                try {
                    //result = bankDao.transfer(fromId， toId， amount);
                    System.out.println("这里是转账");
                } catch (Exception e) {
                    status.setRollbackOnly();
                    result = false;
                    System.out.println("Transfer Error!");
                }
                return result;
            }
        });
    }
}
