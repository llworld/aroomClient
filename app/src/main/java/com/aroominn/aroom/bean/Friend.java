package com.aroominn.aroom.bean;

import com.aroominn.aroom.base.BasicResponse;

import java.io.Serializable;

public class Friend extends BasicResponse implements Serializable {

    public FriendData data;

    public FriendData getData() {
        return data;
    }

    public void setData(FriendData data) {
        this.data = data;
    }
}
