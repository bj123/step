package com.aiwsport.web.verify.verifyaop;

import com.aiwsport.core.StepServerException;
import com.aiwsport.core.StepServerExceptionFactor;
import com.aiwsport.web.utlis.RegexUtil;
import com.aiwsport.web.verify.ParamObjVerify;
import com.aiwsport.web.verify.ParamObjVerifys;
import com.aiwsport.web.verify.ParamVerify;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangjian9
 * @date Created on 2018/3/27
 */
@Component
@Aspect
public class ConfigAop {
    /**
     * 定义有一个切入点，范围为web包下的类
     */
    @Pointcut("execution(public * com.weibo.jerryweb.controller.*.*(..))")
    public void verifyParam() {
    }

    @Before("verifyParam()")
    public void doBefore(JoinPoint joinPoint) {
    }

    /**
     * 检查参数是否为空
     */
    @Around("verifyParam()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = ((MethodSignature) pjp.getSignature());
        //得到拦截的方法
        Method method = signature.getMethod();

        //获取方法参数名
        String[] paramNames = signature.getParameterNames();
        //获取参数值
        Object[] paranValues = pjp.getArgs();
        // 组装参数名称与参数值到集合
        Map<String, Object> paramMap = new HashMap<String, Object>();
        for (int i = 0; i < paramNames.length; i++) {
            paramMap.put(paramNames[i], paranValues[i]);
        }

        // 仅获取当前方法上的注册信息
        Annotation[] methodAnnotations = method.getDeclaredAnnotations();
        if (methodAnnotations == null || methodAnnotations.length == 0) {
            return pjp.proceed();
        }
        // 方法力度注解规则校验
        methodAnnotationVerify(methodAnnotations, paramMap);

        //获取方法参数注解，返回二维数组是因为某些参数可能存在多个注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return pjp.proceed();
        }
        // 参数力度注解规则校验
        paramAnnotationVerify(parameterAnnotations, paramNames, paranValues);
        return pjp.proceed();
    }

    /**
     * 在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
     *
     * @param joinPoint
     */
    @AfterReturning("verifyParam()")
    public void doAfterReturning(JoinPoint joinPoint) {
    }

    /**
     * 参数级别校验注解
     *
     * @param parameterAnnotations
     * @param paramNames
     * @param paramValues
     */
    private void paramAnnotationVerify(Annotation[][] parameterAnnotations, String[] paramNames, Object[] paramValues) {
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                if (parameterAnnotations[i][j] == null) {
                    break;
                }
                if (parameterAnnotations[i][j] instanceof ParamVerify) {
                    verify((ParamVerify) parameterAnnotations[i][j], paramValues[i], paramNames[i]);
                }
                break;
            }
        }
    }

    /**
     * 方法级别校验注解
     *
     * @param methodAnnotations
     * @param paramMap
     */
    private void methodAnnotationVerify(Annotation[] methodAnnotations, Map<String, Object> paramMap) {
        for (Annotation methodAnnotation : methodAnnotations) {
            if (methodAnnotation instanceof ParamObjVerifys) {// 是否是方法校验类型注解，排除其他类型注解
                // 获取ParamObjVerify注解集合
                ParamObjVerify[] ParamObjVerifyList = ((ParamObjVerifys) methodAnnotation).value();

                // ParamObjVerify遍历校验
                for (ParamObjVerify paramObjVerify : ParamObjVerifyList) {
                    // 获取配置校验的参数名，需要校验的属性名
                    String verifyKey = paramObjVerify.verifyKey();
                    if (!verifyKey.contains(".")) {
                        continue;
                    }
                    String paramName = verifyKey.split("\\.")[0];
                    String fieldName = verifyKey.split("\\.")[1];
                    Object paramValue = paramMap.get(paramName);

                    try {
                        Field field = paramValue.getClass().getDeclaredField(fieldName);
                        // 暴力破解，保证对private的属性的访问
                        field.setAccessible(true);
                        verify(paramObjVerify.paramRule(), field.get(paramValue), field.getName());
                    } catch (NoSuchFieldException e) {
                        throw new StepServerException(StepServerExceptionFactor.CONFIG_ERROR);
                    } catch (IllegalAccessException e) {
                        throw new StepServerException(StepServerExceptionFactor.INTERNAL_ERROR);
                    }
                }
            }
        }
    }

    private void verify(ParamVerify paramVerify, Object value, String fieldName) {
        if (paramVerify == null) {
            throw new StepServerException(StepServerExceptionFactor.CONFIG_PARAM_TYPE_MISMATCH);
        }
        String paramValue = value == null ? null : String.valueOf(value);

        if (paramVerify.isNotNull()) {
            if (value == null) {
                throw new StepServerException(StepServerExceptionFactor.PARAM_VERIFY_FAIL, fieldName+"不能为空");
            }
        }
        if (paramVerify.isNotBlank()) {
            if (StringUtils.isBlank(paramValue)) {
                throw new StepServerException(StepServerExceptionFactor.PARAM_VERIFY_FAIL, fieldName+"不能为空");
            }
        }
        if (StringUtils.isNotBlank(paramValue)) {
            if (paramVerify.maxLen() != Integer.MAX_VALUE) {
                if (paramValue.length() > paramVerify.maxLen()) {
                    throw new StepServerException(StepServerExceptionFactor.PARAM_VERIFY_FAIL, fieldName+"长度超过限制");
                }
            }
            if (paramVerify.minLen() != -1) {
                if (paramValue.length() < paramVerify.minLen()) {
                    throw new StepServerException(StepServerExceptionFactor.PARAM_VERIFY_FAIL, fieldName+"长度小于超过限制");
                }
            }
            if (paramVerify.isIp()) {
                if (!RegexUtil.isIp(paramValue)) {
                    throw new StepServerException(StepServerExceptionFactor.PARAM_VERIFY_FAIL, fieldName+"不是有效的ip地址");
                }
            }
            if (paramVerify.isIps()) {
                if (!RegexUtil.isIps(paramValue)) {
                    throw new StepServerException(StepServerExceptionFactor.PARAM_VERIFY_FAIL, fieldName+"不是有效的ip地址");
                }
            }
            if (paramVerify.isNumber()) {
                if (!RegexUtil.isNumber(paramValue)) {
                    throw new StepServerException(StepServerExceptionFactor.PARAM_VERIFY_FAIL, fieldName+"不是正数");
                }
            }
            if (paramVerify.isHostOrIp()) {
                if (!RegexUtil.isIp(paramValue) && !RegexUtil.isHost(paramValue)) {
                    throw new StepServerException(StepServerExceptionFactor.PARAM_VERIFY_FAIL, fieldName+"不是有效的ip或域名");
                }
            }
            if (paramVerify.isBoolean()) {
                if (!RegexUtil.isBoolean(paramValue)) {
                    throw new StepServerException(StepServerExceptionFactor.PARAM_VERIFY_FAIL, fieldName+"不是正确的boolean值");
                }
            }
            if (paramVerify.isNotChinese()) {
                if (RegexUtil.isChinese(paramValue)) {
                    throw new StepServerException(StepServerExceptionFactor.PARAM_VERIFY_FAIL, fieldName+"不可包含汉字");
                }
            }
            if (paramVerify.isNull()) {
                throw new StepServerException(StepServerExceptionFactor.PARAM_VERIFY_FAIL, fieldName+"不可修改");
            }
        }
    }
}
