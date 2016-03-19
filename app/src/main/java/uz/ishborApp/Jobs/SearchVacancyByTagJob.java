package uz.ishborApp.Jobs;

import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.Params;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import uz.ishborApp.AppComponent;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.Entity.DaoMaster;
import uz.ishborApp.Entity.Search;
import uz.ishborApp.Entity.SearchDao;
import uz.ishborApp.Entity.Vacancy;
import uz.ishborApp.Events.VacancyListEvent;
import uz.ishborApp.Fragments.MainFragment;

/**
 * Created by sherxon on 2/14/16.
 */

public class SearchVacancyByTagJob extends BaseJob {
    private  String  word;

    @Inject
    DaoMaster daoMaster;

    public SearchVacancyByTagJob(String word) {
        super(new Params(200).requireNetwork());
        this.word=word;
    }

    @Override
    public void onAdded() {

        SearchDao searchDao=daoMaster.newSession().getSearchDao();
        Search search= searchDao.queryBuilder().where(SearchDao.Properties.Word.eq(word)).build().unique();
        if(search==null)search= new Search();
        search.setWord(word);
        search.setCreated(new Date());
        searchDao.insertOrReplace(search);
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
        Type type=new TypeToken<List<Vacancy>>(){}.getType();
        List<Vacancy> vacancyList=gson.fromJson(result, type);
        EventBus.getDefault().post(new VacancyListEvent(vacancyList, MainFragment.class));
    }

    @Override
    protected void onCancel() {
        EventBus.getDefault().post(false);
    }
    @Override
    protected int getRetryLimit() {
        return 5;
    }
}
