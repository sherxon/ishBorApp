package uz.ishborApp;

import android.app.Activity;

import dagger.Component;
import uz.ishborApp.Entity.DaoMaster;
import uz.ishborApp.Modules.DbModule;

/**
 * Created by sherxon on 1/16/16.
 */
@Component(
        modules = {
                DbModule.class
        }
)
public interface AppComponent {
    void inject(Activity currentActivity);
}
