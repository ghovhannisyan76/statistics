package com.ghovhannisyan.statistics.advices;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
public enum InternalErrorEnum {

    INTERNAL("Internal Server error. Refer server logs for more information."),
    REQUEST_METHOD_NOT_SUPPORTED("Request method is not supported."),
    REQUEST_PAYLOAD_VALIDATION_FAILED("Payload validation failed. The most likely reason are javax.validation rules set on the DTO did not pass."),
    TRANSACTION_TIMESTAMP_CANNOT_NE_IN_FUTURE("Transaction timestamp cannot be in future"),
    TRANSACTION_IS_OLD("Transaction is old");

    private final String description;

    InternalErrorEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
