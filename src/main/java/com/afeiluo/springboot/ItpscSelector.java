package com.afeiluo.springboot;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created by qiaolinfei on 2020/3/26.
 */
public class ItpscSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.afeiluo.springboot.User"};
    }
}
