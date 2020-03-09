package com.aroominn.aroom.bean;

import com.aroominn.aroom.base.BasicResponse;

public class HomeInfo extends BasicResponse {

    InfoData data;

    public InfoData getData() {
        return data;
    }

    public void setData(InfoData data) {
        this.data = data;
    }

    public class InfoData {
        String name;
        String headUrl;
        String follow;
        String isFollow;    //是否关注当前用户 自己id则是  空则否

        public String getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(String isFollow) {
            this.isFollow = isFollow;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
        }
    }

}
