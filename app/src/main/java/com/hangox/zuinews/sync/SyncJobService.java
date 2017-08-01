package com.hangox.zuinews.sync;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

/**
 * Created With Android Studio
 * User HangoX
 * Date 2017/7/31
 * Time 下午8:55
 */

public class SyncJobService extends JobService{
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Intent intent = new Intent(this,NewsUpdateService.class);
        intent.setAction(NewsUpdateService.ACTION_UPDATE);
        startService(intent);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Intent intent = new Intent(this,NewsUpdateService.class);
        intent.setAction(NewsUpdateService.ACTION_STOP);
        startService(intent);
        return false;
    }
}
