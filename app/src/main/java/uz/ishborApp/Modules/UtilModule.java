package uz.ishborApp.Modules;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sherxon on 1/17/16.
 */
@Module
@Singleton
public class UtilModule {

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(){
        return new OkHttpClient();
    }
}
