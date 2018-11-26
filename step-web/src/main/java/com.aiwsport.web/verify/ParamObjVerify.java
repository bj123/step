package com.aiwsport.web.verify;

import java.lang.annotation.*;


/**
 * 对象注解，作用于方法上。
 *
 * @author yangjian9
 * @date Created on 2018/3/28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ParamObjVerifys.class)
public @interface ParamObjVerify {

    // 对象名称.对象属性
    String verifyKey() default "";

    ParamVerify paramRule();
}


