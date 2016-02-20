package uz.ishborApp.Jobs;

import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.Params;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.lang.reflect.Type;
import java.util.List;

import de.greenrobot.event.EventBus;
import uz.ishborApp.Activity.MainActivity;
import uz.ishborApp.AppComponent;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.Entity.Vacancy;
import uz.ishborApp.Events.VacancyListEvent;
import uz.ishborApp.Fragments.SearchResultListFragment;

/**
 * Created by sherxon on 2/14/16.
 */
public class SearchVacancyByTagJob extends BaseJob {
    private  String  word;

    public SearchVacancyByTagJob(String word) {
        super(new Params(200).requireNetwork().persist());
        this.word=word;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void inject(AppComponent appComponent) {
        super.inject(appComponent);
        appComponent.inject(this);
    }

    @Override
    public void onRun() throws Throwable {
        Request request=new Request.Builder().url(Globals.LOCAL_SEARCHBY_TAG+word).build();
        Response response=okHttpClient.newCall(request).execute();
        objectifyRespond(response.body().string());
    }

    private void objectifyRespond(String result) {
        System.out.println(result);
        Type type=new TypeToken<List<Vacancy>>(){}.getType();
        List<Vacancy> vacancyList=gson.fromJson(result, type);
        EventBus.getDefault().post(new VacancyListEvent(vacancyList, SearchResultListFragment.class));
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected int getRetryLimit() {
        return 5;
    }
}
