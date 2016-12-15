package com.afeiluo.spring.mongodb;

import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;

/**
 * 自定义的mongotypemapper
 * 
 * @author qiaolinfei
 * 
 */
public class CustomMongoTypeMapper extends DefaultMongoTypeMapper {

    /**
     * 将_class 转化为 _lei
     */
    public CustomMongoTypeMapper() {
        super("_lei");
    }
}
