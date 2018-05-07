package com.ghovhannisyan.statistics.advices;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.ghovhannisyan.statistics.dtos.ErrorDetails;
import com.ghovhannisyan.statistics.exceptions.BadRequestException;
import com.ghovhannisyan.statistics.exceptions.OldTransactionException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
@ControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ErrorDetails handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        return  new ErrorDetails(
                InternalErrorEnum.REQUEST_METHOD_NOT_SUPPORTED.ordinal(),
                e.getMessage());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorDetails handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        ErrorDetails errorDetails =  new ErrorDetails(
                InternalErrorEnum.REQUEST_PAYLOAD_VALIDATION_FAILED.ordinal());


        BindingResult result = e.getBindingResult();
        List<ObjectError> allErrors = result.getAllErrors();
        for (ObjectError objectError: allErrors) {

            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError)objectError;
                errorDetails.addValidationFieldError(fieldError.getField(),fieldError.getDefaultMessage());
                continue;
            }

            errorDetails.addPayloadError(objectError.getDefaultMessage());
        }

        return errorDetails;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ErrorDetails handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        if (!(e.getCause() instanceof JsonMappingException)) {

            return new ErrorDetails(
                    InternalErrorEnum.REQUEST_PAYLOAD_VALIDATION_FAILED.ordinal(),
                    e.getMostSpecificCause().getMessage());
        }

        Map<String, String> messages = new LinkedHashMap<>();
        JsonMappingException jme = (JsonMappingException)e.getCause();
        for(JsonMappingException.Reference reference : jme.getPath()) {
            if(StringUtils.isNotBlank(reference.getFieldName())) {
                messages.put(reference.getFieldName(), "Invalid");
            }
        }

        return messages.isEmpty() ?
                new ErrorDetails(
                        InternalErrorEnum.REQUEST_PAYLOAD_VALIDATION_FAILED.ordinal(),
                        InternalErrorEnum.REQUEST_PAYLOAD_VALIDATION_FAILED.getDescription()):
                new ErrorDetails(
                        InternalErrorEnum.REQUEST_PAYLOAD_VALIDATION_FAILED.ordinal(),
                        messages);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    @ExceptionHandler(OldTransactionException.class)
    @ResponseBody
    public void handleOldTransactionException(OldTransactionException e) {
        // The body of this method is left empty. It will return an empty body with Response code equal to 204
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ErrorDetails handleBadRequestException(BadRequestException e) {

        return  new ErrorDetails(
                e.getInternalErrorCode().ordinal(),
                e.getMessage());

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorDetails handleException(Exception e) {

        return new ErrorDetails(
                InternalErrorEnum.INTERNAL.ordinal(),
                InternalErrorEnum.INTERNAL.getDescription());
    }
}
