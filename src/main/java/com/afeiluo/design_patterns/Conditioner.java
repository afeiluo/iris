package com.afeiluo.design_patterns;

import com.afeiluo.design_patterns.model.Model;

/**
 * @author ben
 * @date 2019/9/5
 */
public interface Conditioner {

    public boolean match(Model bizModel);
}
