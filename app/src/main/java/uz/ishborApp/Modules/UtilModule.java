package uz.ishborApp.Modules;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

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

    @Provides
    @Singleton
    Gson provideGson(){
        return new Gson();
    }

}
