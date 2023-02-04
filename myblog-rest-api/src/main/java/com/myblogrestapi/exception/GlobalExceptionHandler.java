package com.myblogrestapi.exception;

import com.myblogrestapi.payload.ErrorDtails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResoueceNotFoundException.class)
    public ResponseEntity<ErrorDtails> ResourceNotFoundException(ResoueceNotFoundException exception, WebRequest webRequest){
        ErrorDtails errordetails = new ErrorDtails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return  new ResponseEntity<>(errordetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDtails> globalExceptionHandler(Exception exception, WebRequest webRequest){
        ErrorDtails errorDtails = new ErrorDtails(new Date(), exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDtails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
