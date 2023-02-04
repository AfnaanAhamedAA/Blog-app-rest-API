package com.myblogrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResoueceNotFoundException extends RuntimeException{
    private String resourcename;
    private String fieldname;
    private long fieldvalue;

    public ResoueceNotFoundException(String resourcename, String fieldname, long fieldvalue) {
        super(String.format("%s not found with %s : '%s'",resourcename,fieldname,fieldvalue));

        this.resourcename = resourcename;
        this.fieldname = fieldname;
        this.fieldvalue = fieldvalue;
    }

    public String getResourcename() {
        return resourcename;
    }

    public String getFieldname() {
        return fieldname;
    }

    public long getFieldvalue() {
        return fieldvalue;
    }
}
