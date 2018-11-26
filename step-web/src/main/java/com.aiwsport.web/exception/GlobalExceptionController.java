package com.aiwsport.web.exception;

import com.aiwsport.core.StepServerException;
import com.aiwsport.core.StepServerExceptionFactor;
import com.aiwsport.core.constant.ResultMsg;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yanggjian
 */
@RestControllerAdvice
public class GlobalExceptionController {
    private Logger logger = LogManager.getLogger();

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultMsg handleAllException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        StepServerException stepServerException;
        if (exception instanceof StepServerException) {
            stepServerException = (StepServerException) exception;
        } else if (exception instanceof ServletRequestBindingException) {
            stepServerException = new StepServerException(StepServerExceptionFactor.MISSING_PARAM, exception.getMessage());
        } else if (exception instanceof MethodArgumentTypeMismatchException) {
            String paramName = ((MethodArgumentTypeMismatchException) exception).getParameter().getParameterName();
            String errorMsg = StepServerExceptionFactor.CONFIG_PARAM_TYPE_MISMATCH.getErrorMsg() + " [" + paramName + "]";
            stepServerException = new StepServerException(StepServerExceptionFactor.CONFIG_PARAM_TYPE_MISMATCH, errorMsg);
        } else if (exception instanceof BindException) {
            BindException result = (BindException) exception;
            FieldError fieldError = result.getFieldError();
            stepServerException = new StepServerException(StepServerExceptionFactor.BIND_ERROR, fieldError.getField()+"绑定失败，请校验参数类型");
        } else if (exception instanceof NumberFormatException) {
            stepServerException = new StepServerException(StepServerExceptionFactor.PATTERN_ERROR, exception.getMessage());
        } else {
            stepServerException = new StepServerException(StepServerExceptionFactor.DEFAULT);
            logger.error(ExceptionUtils.getStackTrace(exception));
        }

        logger.error(ExceptionUtils.getStackTrace(exception));
        ResultMsg resultMsg = new ResultMsg(false, stepServerException.getFactor().getErrorCode(), stepServerException.getMessage());
        return resultMsg;
    }
}
