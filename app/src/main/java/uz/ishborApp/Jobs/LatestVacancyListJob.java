package uz.ishborApp.Jobs;

import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.Params;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.lang.reflect.Type;
import java.util.List;

import de.greenrobot.event.EventBus;
import uz.ishborApp.AppComponent;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.Entity.Vacancy;
import uz.ishborApp.Events.VacancyListEvent;
import uz.ishborApp.Fragments.MainFragment;

/**
 * Created by sherxon on 3/15/16.
 */
public class LatestVacancyListJob extends BaseJob{

    public LatestVacancyListJob() {
        super(new Params(900));
    }

    @Override
    public void inject(AppComponent appComponent) {
        super.inject(appComponent);
        appComponent.inject(this);
    }


    @Override
    public void onAdded() {

    }


    @Override
    public void onRun() throws Throwable {
        Request request=new  Request.Builder().url(Globals.LOCAL_LATEST_URL).build();
        Response response=okHttpClient.newCall(request).execute();
        String result=response.body().string();
        Type type=new TypeToken<List<Vacancy>>(){}.getType();
        List<Vacancy> vacancyList =gson.fromJson(result, type);
        EventBus.getDefault().post(new VacancyListEvent(vacancyList, MainFragment.class));

    }

    @Override
    protected int getRetryLimit() {
        return 5;
    }

    @Override
    protected void onCancel() {
        EventBus.getDefault().post(false);
    }

}
