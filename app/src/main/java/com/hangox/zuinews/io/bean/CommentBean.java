package com.hangox.zuinews.io.bean;

/**
 * Created With Android Studio
 * User HangoX
 * Date 2017/7/30
 * Time 下午5:54
 */

public class CommentBean {
    private String userName;
    private String comment;
    private String date;
    private String imageUrl;


    private long time;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommentBean{");
        sb.append("userName='").append(userName).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }

    public long getTime() {
        return time;
    }

    public CommentBean setTime(long time) {
        this.time = time;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public CommentBean setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public CommentBean setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getDate() {
        return date;
    }

    public CommentBean setDate(String date) {
        this.date = date;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CommentBean setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

}
