package com.hangox.zuinews.io;

import android.content.Context;

import com.hangox.zuinews.io.entry.DaoMaster;
import com.hangox.zuinews.io.entry.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/11
 * Time 上午9:44
 */

public class Db  {
    public static final Db INSTANCE =  new Db();
    private static DaoSession mDaoSession;

    private Db(){}

    public static final void init(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "news-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession session(){
        return mDaoSession;
    }
}
