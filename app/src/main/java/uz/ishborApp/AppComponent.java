package uz.ishborApp;

import com.path.android.jobqueue.JobManager;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;
import uz.ishborApp.Activity.BaseDrawerActivity;
import uz.ishborApp.Activity.MainActivity;

import uz.ishborApp.Fragments.CategoryFragment;
import uz.ishborApp.Fragments.MainFragment;
import uz.ishborApp.Fragments.VacancyDesc;
import uz.ishborApp.Jobs.CategoryListJob;
import uz.ishborApp.Jobs.LatestVacancyListJob;
import uz.ishborApp.Jobs.SaveVacancyJob;
import uz.ishborApp.Jobs.SearchTagJob;
import uz.ishborApp.Jobs.SearchVacancyByTagJob;
import uz.ishborApp.Jobs.VacancyListJob;
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

    void inject(MainActivity mainActivity);

    void inject(MyApplication application);

    void inject(JobManager jobManager);

    void inject(CategoryListJob categoryListJob);

    void inject(VacancyListJob vacancyListJob);

    void inject(SearchTagJob searchTagJob);

    void inject(SearchVacancyByTagJob searchVacancyByTagJob);

    void inject(MainFragment mainFragment);

    void inject(CategoryFragment categoryFragment);

    void inject(LatestVacancyListJob latestVacancyListJob);

    void inject(VacancyDesc vacancyDesc);

    void inject(SaveVacancyJob saveVacancyJob);
}
