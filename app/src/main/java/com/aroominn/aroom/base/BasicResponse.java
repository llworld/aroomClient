package com.aroominn.aroom.base;

import java.io.Serializable;

public class BasicResponse<T> implements Serializable {

    private Integer code;
    private String message;


    public Integer getStatus_code() {
        return code;
    }

    public void setStatus_code(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}