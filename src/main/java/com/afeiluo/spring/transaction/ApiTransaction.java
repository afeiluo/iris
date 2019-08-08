package com.afeiluo.spring.transaction;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

/**
 * @author ben
 * @date 2019/8/8
 */
public class ApiTransaction {
    //    private BankDao bankDao;
    //定义事务的规则
    private TransactionDefinition txDefinition;

    //执行事务管理操作
    private PlatformTransactionManager txManager;

    public static void main(String[] args) {

    }

    /**
     * 转账
     *
     * @param fromId
     * @param toId
     * @param amount
     * @return
     */
    public boolean transfer(Long fromId, Long toId, double amount) {
        // 获取一个事务
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);
        boolean result = false;
        try {
            //result = bankDao.transfer(fromId, toId, amount);
            System.out.println("这里是转账");
            txManager.commit(txStatus);    // 事务提交
        } catch (Exception e) {
            result = false;
            txManager.rollback(txStatus);      // 事务回滚
            System.out.println("Transfer Error!");
        }
        return result;
    }
}
