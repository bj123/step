package com.aiwsport.core;

/**
 * xin.pang
 */
public class StepServerException extends RuntimeException {
    private StepServerExceptionFactor factor;

    public StepServerException(StepServerExceptionFactor factor) {
        this(factor, factor.getErrorMsg());
    }

    public StepServerException(StepServerExceptionFactor factor, String message) {
        super((message == null ? factor.getErrorMsg() : message));
        this.factor = factor;
    }

    public StepServerException(Exception e) {
        this(StepServerExceptionFactor.DEFAULT, e.getMessage());
    }

    public StepServerExceptionFactor getFactor() {
        return factor;
    }
}
