package com.afeiluo.design_patterns.handler;

import com.afeiluo.design_patterns.model.Model;

/**
 * @author ben
 * @date 2019/9/5
 */
public interface Handler {
    public void next(Handler handler);

    public void handle(Model model);
}
