package uz.ishborApp;

import android.app.Application;
import android.content.Context;

import uz.ishborApp.Modules.DbModule;
import uz.ishborApp.Modules.UtilModule;

/**
 * Created by sherxon on 1/16/16.
 */
public class MyApplication extends Application {
    private AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent=DaggerAppComponent.builder().utilModule(new UtilModule()).dbModule(new DbModule(this)).build();
        appComponent.inject(this);
    }

    public static MyApplication get(Context context) {
        return ((MyApplication)context.getApplicationContext());
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
