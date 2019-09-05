package com.afeiluo.design_patterns;

import com.afeiluo.design_patterns.handler.Handler;
import com.afeiluo.design_patterns.handler.LockHandler;
import com.afeiluo.design_patterns.handler.LogHandler;
import com.afeiluo.design_patterns.model.BizModel;
import com.afeiluo.design_patterns.model.Model;

/**
 * @author ben
 * @date 2019/9/5
 */
public class HttpEntry implements Entry {
    private Handler startHandler;

    public HttpEntry(Handler handler) {
        this.startHandler = handler;
    }

    @Override
    public void start(Model bizModel) {
        startHandler.handle(bizModel);
    }


    public static void main(String[] args) {
        LockHandler lockHandler = new LockHandler();
        LogHandler logHandler = new LogHandler();
        lockHandler.next(logHandler);

        HttpEntry entry = new HttpEntry(lockHandler);
        entry.start(new BizModel());
    }
}
