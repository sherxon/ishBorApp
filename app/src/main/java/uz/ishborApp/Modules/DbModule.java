package uz.ishborApp.Modules;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.path.android.jobqueue.JobManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uz.ishborApp.Entity.DaoMaster;
import uz.ishborApp.MyApplication;

/**
 * Created by sherxon on 1/17/16.
 */
@Module
public class DbModule {
    private final MyApplication myApplication;

    public DbModule(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Provides
    @Singleton
    DaoMaster provideDaoMaster(SQLiteOpenHelper db){
        DaoMaster daoMaster= new DaoMaster(db.getWritableDatabase());
        DaoMaster.createAllTables(db.getWritableDatabase(), true);
        return daoMaster;
    }
    @Provides @Singleton
    SQLiteOpenHelper provideSQLiteOpenHelper(Context context){
        return new DaoMaster.DevOpenHelper(context, "test-db", null);
    }

    @Provides
    Context provideContext(){
        return myApplication.getApplicationContext();
    }

    @Provides
    MyApplication provideMyApplication(){
        return myApplication;
    }


    @Provides @Singleton
    JobManager provideJobManager(MyApplication application){
        return application.getJobManager();
    }
}
