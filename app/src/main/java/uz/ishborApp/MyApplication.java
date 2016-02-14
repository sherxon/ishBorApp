package uz.ishborApp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.di.DependencyInjector;
import com.path.android.jobqueue.log.CustomLogger;

import uz.ishborApp.Jobs.BaseJob;
import uz.ishborApp.Modules.DbModule;
import uz.ishborApp.Modules.UtilModule;

/**
 * Created by sherxon on 1/16/16.
 */
public class MyApplication extends Application {
    private AppComponent appComponent;
    private JobManager jobManager;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent=DaggerAppComponent.builder().utilModule(new UtilModule()).dbModule(new DbModule(this)).build();
        appComponent.inject(this);
        configureJobManager();
        appComponent.inject(jobManager);
    }

    public JobManager getJobManager() {
        return jobManager;
    }

    public static MyApplication get(Context context) {
        return ((MyApplication)context.getApplicationContext());
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }



    private void configureJobManager() {
        Configuration configuration = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .injector(new DependencyInjector() {
                    @Override
                    public void inject(Job job) {
                        if (job instanceof BaseJob) {
                            ((BaseJob) job).inject(getAppComponent());
                        }
                    }
                })
                .build();
        jobManager = new JobManager(this, configuration);
    }
}
