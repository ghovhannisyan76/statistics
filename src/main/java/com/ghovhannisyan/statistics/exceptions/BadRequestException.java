package com.ghovhannisyan.statistics.exceptions;

import com.ghovhannisyan.statistics.advices.InternalErrorEnum;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
public class BadRequestException extends AbstractRESTException {

    private static final long serialVersionUID = -565857061513179654L;

    public BadRequestException(
            InternalErrorEnum internalErrorCode,
            String message) {

        super(internalErrorCode, message);
    }

    public BadRequestException(
            InternalErrorEnum internalErrorCode,
            String message,
            Throwable throwable) {

        super(internalErrorCode, message, throwable);
    }
}
