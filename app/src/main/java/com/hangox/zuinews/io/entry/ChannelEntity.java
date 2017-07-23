package com.hangox.zuinews.io.entry;

import com.hangox.zuinews.io.bean.ChannelBean;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.Date;
import java.util.List;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/10
 * Time 下午10:52
 */

@Entity
public class ChannelEntity {

    @Id
    private String channelId;
    private String name;

    private Date createTime;



    @ToMany(referencedJoinProperty = "channelId" )
    private List<NewsEntity> newsEntities;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1096940961)
    private transient ChannelEntityDao myDao;

    public ChannelEntity(ChannelBean bean) {
        channelId = bean.getChannelId();
        name = bean.getName();
        createTime = new Date();
    }

    @Generated(hash = 781881457)
    public ChannelEntity() {
    }

    @Generated(hash = 679473028)
    public ChannelEntity(String channelId, String name, Date createTime) {
        this.channelId = channelId;
        this.name = name;
        this.createTime = createTime;
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
    


    public Date getCreateTime() {
        return this.createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 533877369)
    public List<NewsEntity> getNewsEntities() {
        if (newsEntities == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NewsEntityDao targetDao = daoSession.getNewsEntityDao();
            List<NewsEntity> newsEntitiesNew = targetDao
                    ._queryChannelEntity_NewsEntities(channelId);
            synchronized (this) {
                if (newsEntities == null) {
                    newsEntities = newsEntitiesNew;
                }
            }
        }
        return newsEntities;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2061890552)
    public synchronized void resetNewsEntities() {
        newsEntities = null;
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1862996460)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getChannelEntityDao() : null;
    }


}
