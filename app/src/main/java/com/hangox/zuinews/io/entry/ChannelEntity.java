package com.hangox.zuinews.io.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/10
 * Time 下午10:52
 */

@Entity
public class ChannelEntity {
    @Id(autoincrement = true)
    private long id;

    private String channelId;
    private String name;

    @Generated(hash = 1957092048)
    public ChannelEntity(long id, String channelId, String name) {
        this.id = id;
        this.channelId = channelId;
        this.name = name;
    }

    @Generated(hash = 781881457)
    public ChannelEntity() {
    }

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

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
