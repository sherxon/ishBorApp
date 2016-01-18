package uz.ishborApp;

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;
import uz.ishborApp.Modules.DbModule;
import uz.ishborApp.Modules.UtilModule;

/**
 * Created by sherxon on 1/16/16.
 */

@Singleton
@Component(
        modules = {
                UtilModule.class,
                DbModule.class
        }
)
public interface AppComponent {

    OkHttpClient okHttpClient();

    void inject(BaseDrawerActivity currentActivity);
    void inject(MyApplication application);
}
