package com.aroominn.aroom.bean;

import com.aroominn.aroom.base.BasicResponse;

import java.io.Serializable;

/**
 * Author : yml
 * Created on 2018-3-6.
 */

public class Result extends BasicResponse implements Serializable {

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
