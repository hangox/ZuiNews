package com.hangox.zuinews.io.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created With Android Studio
 * User HangoX
 * Date 2017/7/30
 * Time 下午5:54
 */

public class CommentBean implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.comment);
        dest.writeString(this.date);
        dest.writeString(this.imageUrl);
        dest.writeLong(this.time);
    }

    public CommentBean() {
    }

    protected CommentBean(Parcel in) {
        this.userName = in.readString();
        this.comment = in.readString();
        this.date = in.readString();
        this.imageUrl = in.readString();
        this.time = in.readLong();
    }

    public static final Parcelable.Creator<CommentBean> CREATOR = new Parcelable.Creator<CommentBean>() {
        public CommentBean createFromParcel(Parcel source) {
            return new CommentBean(source);
        }

        public CommentBean[] newArray(int size) {
            return new CommentBean[size];
        }
    };
}
