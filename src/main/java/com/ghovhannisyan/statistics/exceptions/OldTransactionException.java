package com.ghovhannisyan.statistics.exceptions;

import com.ghovhannisyan.statistics.advices.InternalErrorEnum;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
public class OldTransactionException extends AbstractRESTException {

    private static final long serialVersionUID = -6322007172301383976L;

    public OldTransactionException(
            InternalErrorEnum internalErrorCode,
            String message) {

        super(internalErrorCode, message);
    }

    public OldTransactionException(
            InternalErrorEnum internalErrorCode,
            String message,
            Throwable throwable) {

        super(internalErrorCode, message, throwable);
    }
}
