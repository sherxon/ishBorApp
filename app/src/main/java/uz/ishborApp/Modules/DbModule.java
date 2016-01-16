package uz.ishborApp.Modules;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uz.ishborApp.DAO.DbBalance;
import uz.ishborApp.Entity.DaoMaster;

/**
 * Created by sherxon on 1/16/16.
 */
@Module
public class DbModule {

   private SQLiteOpenHelper db ;
   private DaoMaster daoMaster;

    @Provides @Singleton
    DaoMaster provideDaoMaster(SQLiteOpenHelper db){
        return daoMaster= new DaoMaster(db.getWritableDatabase());
    }

    @Provides @Singleton
    SQLiteOpenHelper provideSQLiteOpenHelper(Context context){
        return db =new DaoMaster.DevOpenHelper(context, "test-db", null);
    }
    @Provides @Singleton
    OkHttpClient provideHttpClient(){
        return new OkHttpClient();
    }
//    @Provides @Singleton
//    DbBalance provideDbBalance(){
//        return new DbBalance();
//    }

}
