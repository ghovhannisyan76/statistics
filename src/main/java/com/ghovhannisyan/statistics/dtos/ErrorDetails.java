package com.ghovhannisyan.statistics.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

import java.util.*;

/**
 * The response payload in case an exception occurs.
 *
 * Created by ghovhannisyan on 5/5/18.
 */
public class ErrorDetails {

    private final int internalCode;

    private final Map<String, String> validationFieldErrors = new LinkedHashMap<>();

    private final Set<String> payloadErrors = new HashSet<>();

    @JsonCreator
    public ErrorDetails(
            @JsonProperty("internalCode") int internalCode,
            @JsonProperty("validationFieldErrors") Map<String, String> validationFieldErrors,
            @JsonProperty("payloadErrors") Set<String> payloadErrors) {

        this.internalCode = internalCode;
        this.validationFieldErrors.putAll(validationFieldErrors);
        this.payloadErrors.addAll(payloadErrors);
    }

    public ErrorDetails(
            int internalCode,
            Map<String, String> validationErrors) {

        this(internalCode, validationErrors, new HashSet<>());
    }

    public ErrorDetails(
            int internalCode,
            String message) {

        this(internalCode, new HashMap<>(), ImmutableSet.of(message));
    }

    public ErrorDetails(
            int internalCode) {

        this(internalCode, new HashMap<>(), new HashSet<>());
    }

    public int getInternalCode() {
        return internalCode;
    }

    public Map<String, String> getValidationFieldErrors() {
        return Collections.unmodifiableMap(validationFieldErrors);
    }

    public Set<String> getPayloadErrors() {
        return Collections.unmodifiableSet(payloadErrors);
    }

    public void addValidationFieldError(String attribute, String message) {
        validationFieldErrors.put(attribute, message);
    }

    public void addPayloadError(String message) {
        payloadErrors.add(message);
    }
}
