package com.aroominn.aroom.bean;

import com.aroominn.aroom.base.BasicResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class Friend extends BasicResponse implements Serializable {

    public ArrayList<FriendData> data;

    public ArrayList<FriendData> getData() {
        return data;
    }

    public void setData(ArrayList<FriendData> data) {
        this.data = data;
    }
}
