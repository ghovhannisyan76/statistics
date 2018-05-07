package com.ghovhannisyan.statistics.exceptions;

import com.ghovhannisyan.statistics.advices.InternalErrorEnum;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
public abstract class AbstractRESTException extends RuntimeException {

    private static final long serialVersionUID = -3724229344602812569L;

    private final InternalErrorEnum internalErrorCode;

    AbstractRESTException(
            InternalErrorEnum internalErrorCode,
            String message) {
        this(internalErrorCode, message, null);
    }

    AbstractRESTException(
            InternalErrorEnum internalErrorCode,
            String message,
            Throwable throwable) {

        super(message, throwable);

        this.internalErrorCode = internalErrorCode;
    }

    public InternalErrorEnum getInternalErrorCode() {
        return internalErrorCode;
    }
}
