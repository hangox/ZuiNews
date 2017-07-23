package com.hangox.zuinews.io.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/5
 * Time 上午9:42
 */
public class ChannelBean {
    /**
     * channelId : 5572a108b3cdc86cf39001cd
     * name : 国内焦点
     */


    @SerializedName("channelId")
    private String channelId;
    @SerializedName("name")
    private String name;


    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ChannelBean{");
        sb.append("channelId='").append(channelId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
