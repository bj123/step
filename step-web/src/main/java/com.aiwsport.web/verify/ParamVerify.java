package com.aiwsport.web.verify;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * "参数不能为空"注解，作用于方法参数上。
 *
 * @author yangjian9
 * @date Created on 2018/3/27
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamVerify {

    // 是否非空,默认不能为空
    boolean isNotNull() default false;

    // 不为空，不为空字符
    boolean isNotBlank() default false;

    // 是否是有效ip地址
    boolean isIp() default false;

    // 是否是有效ip地址段
    boolean isIps() default false;

    boolean isHostOrIp() default false;

    // 是否是纯数字
    boolean isNumber() default false;

    // 最大长度
    int maxLen() default Integer.MAX_VALUE;

    // 最小长度
    int minLen() default -1;

    // 是否是boolean值
    boolean isBoolean() default false;

    // 不包含汉字
    boolean isNotChinese() default false;

    // 需要是空
    boolean isNull() default false;
}
