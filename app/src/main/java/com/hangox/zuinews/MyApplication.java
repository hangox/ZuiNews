package com.hangox.zuinews;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;

import com.facebook.stetho.Stetho;
import com.hangox.zuinews.io.Db;
import com.hangox.zuinews.io.network.RequestManager;
import com.hangox.zuinews.sync.SyncJobService;
import com.tencent.bugly.Bugly;

import timber.log.Timber;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/4
 * Time 下午10:20
 */

public class MyApplication extends Application {
    private static final int JOB_ID = BuildConfig.APPLICATION_ID.hashCode();
    //一个小时
    public static final int SYNC_PERIOD = 1 * 60 * 60* 1000;
//    public static final int SYNC_PERIOD = 1 * 60* 1000;

    private static final int INITIAL_BACKOFF = 10000;
    @Override
    public void onCreate() {
        super.onCreate();
        Db.init(this);
        Stetho.initializeWithDefaults(this);
        RequestManager.I.init(this);
        Timber.plant(new Timber.DebugTree());
        createSyncJob();
        Bugly.init(getApplicationContext(), BuildConfig.BUGLY_ID, false);
    }

    private void createSyncJob() {
        Timber.d("create job jobId=%d",JOB_ID);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, SyncJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(SYNC_PERIOD)
                .setBackoffCriteria(INITIAL_BACKOFF, JobInfo.BACKOFF_POLICY_EXPONENTIAL);
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.schedule(builder.build());

    }
}
