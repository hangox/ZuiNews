package com.hangox.zuinews.io.entry;

import android.os.Parcel;
import android.os.Parcelable;

import com.hangox.zuinews.io.bean.NewsContentBean;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/18
 * Time 上午9:37
 */

@Entity
public class NewsEntity implements Parcelable {

    @Id
    private String id;


    private String title;


    private String date;


    private String channelId;


    @ToOne(joinProperty = "channelId")
    private ChannelEntity channel;


    private String desc;

    private String link;

    private boolean hasAll;


    private String source;


    private String imageUrl;

    /**
     * html 内容
     */
    private String contentHtml;

    @Generated(hash = 2121778047)
    public NewsEntity() {
    }


    public NewsEntity(NewsContentBean bean) {
        this.id = bean.getId();
        this.title = bean.getTitle();
        this.date = bean.getPubDate();
        this.desc = bean.getDesc();
        this.channelId = bean.getChannelId();
        if (!bean.getImageurls().isEmpty()) {
            this.imageUrl = bean.getImageurls().get(0).getUrl();
        } else {
            this.imageUrl = "";
        }
        this.source = bean.getSource();
        this.hasAll =bean.isHasAll();
        this.link = bean.getLink();
        this.contentHtml = bean.getHtml();


    }

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;


    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1760664272)
    private transient NewsEntityDao myDao;


    @Generated(hash = 465162691)
    private transient String channel__resolvedKey;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return this.title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getDate() {
        return this.date;
    }


    public void setDate(String date) {
        this.date = date;
    }


    public String getDesc() {
        return this.desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getLink() {
        return this.link;
    }


    public void setLink(String link) {
        this.link = link;
    }


    public boolean getHasAll() {
        return this.hasAll;
    }


    public void setHasAll(boolean hasAll) {
        this.hasAll = hasAll;
    }


    public String getSource() {
        return this.source;
    }


    public void setSource(String source) {
        this.source = source;
    }


    public String getImageUrl() {
        return this.imageUrl;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getChannelId() {
        return this.channelId;
    }


    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }


    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1519579797)
    public ChannelEntity getChannel() {
        String __key = this.channelId;
        if (channel__resolvedKey == null || channel__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ChannelEntityDao targetDao = daoSession.getChannelEntityDao();
            ChannelEntity channelNew = targetDao.load(__key);
            synchronized (this) {
                channel = channelNew;
                channel__resolvedKey = __key;
            }
        }
        return channel;
    }


    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1411115204)
    public void setChannel(ChannelEntity channel) {
        synchronized (this) {
            this.channel = channel;
            channelId = channel == null ? null : channel.getChannelId();
            channel__resolvedKey = channelId;
        }
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    public String getContentHtml() {
        return this.contentHtml;
    }


    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }


    @Generated(hash = 443335874)
    public NewsEntity(String id, String title, String date, String channelId, String desc,
            String link, boolean hasAll, String source, String imageUrl, String contentHtml) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.channelId = channelId;
        this.desc = desc;
        this.link = link;
        this.hasAll = hasAll;
        this.source = source;
        this.imageUrl = imageUrl;
        this.contentHtml = contentHtml;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.channelId);
        dest.writeString(this.desc);
        dest.writeString(this.link);
        dest.writeByte(this.hasAll ? (byte) 1 : (byte) 0);
        dest.writeString(this.source);
        dest.writeString(this.imageUrl);
        dest.writeString(this.contentHtml);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NewsEntity{");
        sb.append("id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", channelId='").append(channelId).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", link='").append(link).append('\'');
        sb.append(", hasAll=").append(hasAll);
        sb.append(", source='").append(source).append('\'');
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append(", contentHtml='").append(contentHtml).append('\'');
        sb.append(", myDao=").append(myDao);
        sb.append('}');
        return sb.toString();
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1204874507)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNewsEntityDao() : null;
    }


    protected NewsEntity(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.date = in.readString();
        this.channelId = in.readString();
        this.desc = in.readString();
        this.link = in.readString();
        this.hasAll = in.readByte() != 0;
        this.source = in.readString();
        this.imageUrl = in.readString();
        this.contentHtml = in.readString();
    }

    public static final Creator<NewsEntity> CREATOR = new Creator<NewsEntity>() {
        @Override
        public NewsEntity createFromParcel(Parcel source) {
            return new NewsEntity(source);
        }

        @Override
        public NewsEntity[] newArray(int size) {
            return new NewsEntity[size];
        }
    };
}
