package com.finleap.weather.forecast.server.handler;



import com.finleap.weather.forecast.api.dto.error.JsonParseException;
import com.finleap.weather.forecast.api.dto.error.RestException;
import com.finleap.weather.forecast.server.dto.ErrorDetail;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * Created by Dogukan Duman on 8.11.2018.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Handles HttpMessageNotReadableException
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.debug("HttpMessageNotReadableException is handled");
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Message Not Readable");
        errorDetail.setDetail(ex.getMessage());
        return handleExceptionInternal(ex, errorDetail, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handles MethodArgumentNotValidException
     *
     * @param manve
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manve, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.debug("MethodArgumentNotValidException is handled");
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        errorDetail.setTitle("Validation Failed");
        errorDetail.setDetail(manve.getMessage());

        return handleExceptionInternal(manve, errorDetail, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    /**
     * Handles JsonParseException if object mapper throws exception
     * @param jpe
     * @param request
     * @return
     */

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<?> handleJsonParseException(JsonParseException jpe, WebRequest request) {
        logger.debug("JsonParseException is handled");
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetail.setTitle("Problem occurred when mapping Weather Api response object");
        errorDetail.setDetail(jpe.getMessage());
        return handleExceptionInternal(jpe, errorDetail, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Handles RestException when making request to Weather api.
     * @param re
     * @param request
     * @return
     */
    @ExceptionHandler(RestException.class)
    public ResponseEntity<?> handleRestException(RestException re, WebRequest request) {
        logger.debug("RestException is handled");
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetail.setTitle("Problem occurred when making request to Weather api. Wrong url or system problem at Weather api");
        errorDetail.setDetail(re.getMessage());
        return handleExceptionInternal(re, errorDetail, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


}