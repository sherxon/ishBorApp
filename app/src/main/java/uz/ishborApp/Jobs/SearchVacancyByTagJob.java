package uz.ishborApp.Jobs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.Params;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;
import uz.ishborApp.Activity.MainActivity;
import uz.ishborApp.AppComponent;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.Entity.Search;
import uz.ishborApp.Entity.Vacancy;
import uz.ishborApp.Events.VacancyListEvent;

/**
 * Created by sherxon on 2/14/16.
 */
public class SearchVacancyByTagJob extends BaseJob {
    private transient Search search;

    public SearchVacancyByTagJob(Search search) {
        super(new Params(200).requireNetwork().persist());
        this.search=search;
    }

    @Override
    public void onAdded() {
        search.setCreated(new Date());
        daoMaster.newSession().getSearchDao().insertOrReplace(search);
    }

    @Override
    public void inject(AppComponent appComponent) {
        super.inject(appComponent);
        appComponent.inject(this);
    }

    @Override
    public void onRun() throws Throwable {
        Request request=new Request.Builder().url(Globals.LOCAL_SEARCHBY_TAG+search.getWord()).build();
        Response response=okHttpClient.newCall(request).execute();
        objectifyRespond(response.body().string());
    }

    private void objectifyRespond(String result) {
        System.out.println(result);
        Type type=new TypeToken<Vacancy>(){}.getType();
        List<Vacancy> vacancyList=gson.fromJson(result, type);
        EventBus.getDefault().post(new VacancyListEvent(vacancyList, MainActivity.class));
    }

    @Override
    protected void onCancel() {

    }
}
