package com.afeiluo.design_patterns.handler;

import com.afeiluo.design_patterns.Conditioner;
import com.afeiluo.design_patterns.model.Model;

/**
 * @author ben
 * @date 2019/9/5
 */
public class LockHandler implements Handler, Conditioner {
    private Handler nextHandler;

    @Override
    public void next(Handler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handle(Model bizModel) {
        if (this.match(bizModel)) {
            //TODO  处理锁定工单
        } else {
            this.nextHandler.handle(bizModel);
        }

    }

    @Override
    public boolean match(Model bizModel) {
        //TODO 根据 bizModel 来判断是否需要处理
        return false;
    }
}
