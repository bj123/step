package com.aiwsport.web.verify;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * jdk8支持重复注解
 *
 * @author yangjian9
 * @date Created on 2018/3/28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamObjVerifys {

    ParamObjVerify[] value();
}
