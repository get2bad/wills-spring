package com.wills.spring.annotation;

import java.lang.annotation.*;

/**
 * @ClassName Mapper
 * @Date 2022/3/29 15:51
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Mapper{
}
