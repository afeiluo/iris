package com.afeiluo.springboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by qiaolinfei on 2020/3/26.
 */
@Configuration
@Import(ItpscSelector.class)
public class ImportConfig {
}
