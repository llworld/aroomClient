package com.aroominn.aroom.bean;

import com.aroominn.aroom.base.BasicResponse;

import java.io.Serializable;
import java.util.Date;

public class Comment  {//BasicResponse implements Serializable {
/*    public CommentData data;

    public CommentData getData() {
        return data;
    }

    public void setData(CommentData data) {
        this.data = data;
    }

    public class CommentData {*/
        private int id;
        private int storyId;        //    故事ID
        private String content;     //评论内容
        private String times;       //评论时间
        private int from_uid;       //评论的人
        private int to_uid;         //评论目标人的id，如果没有目标人，则该字段为空
        private int type;           //评论类型
        private String head;        //评论者头像
        private String nick;        //评论者昵称

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStoryId() {
            return storyId;
        }

        public void setStoryId(int storyId) {
            this.storyId = storyId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public int getFrom_uid() {
            return from_uid;
        }

        public void setFrom_uid(int from_uid) {
            this.from_uid = from_uid;
        }

        public int getTo_uid() {
            return to_uid;
        }

        public void setTo_uid(int to_uid) {
            this.to_uid = to_uid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }
//    }
}
