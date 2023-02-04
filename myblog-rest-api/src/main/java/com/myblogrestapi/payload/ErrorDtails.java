package com.myblogrestapi.payload;

import java.util.Date;

public class ErrorDtails {

    private Date TimeStamp;
    private String message;
    private String dettails;

    public ErrorDtails(Date timeStamp, String message, String dettails) {
        TimeStamp = timeStamp;
        this.message = message;
        this.dettails = dettails;
    }

    public Date getTimeStamp() {
        return TimeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDettails() {
        return dettails;
    }
}
