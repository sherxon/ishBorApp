package uz.ishborApp.Jobs;

import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.Params;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import de.greenrobot.event.EventBus;
import uz.ishborApp.AppComponent;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.Entity.Category;
import uz.ishborApp.Entity.Vacancy;
import uz.ishborApp.Entity.VacancyDao;
import uz.ishborApp.Events.VacancyListEvent;

/**
 * Created by sherxon on 2/13/16.
 */
public class VacancyListJob extends BaseJob{

    Long categoryId;


    public VacancyListJob(Long categoryId) {
        super(new Params(900).persist());
        this.categoryId=categoryId;
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

        VacancyDao.createTable(daoMaster.getDatabase(), true);
        Category category=daoMaster.newSession().getCategoryDao().load(categoryId);
        List<Vacancy> vacancyList=category.getVacancyList();
        if(vacancyList.size()==0)
            loadVacancyFromApi();
        else
            EventBus.getDefault().post(new VacancyListEvent(vacancyList));

    }

    private void loadVacancyFromApi() throws IOException {
        Request request= new Request.Builder().url(Globals.LOCAL_CATEGORY_URL+"/"+categoryId+"/vacancy").build();
        Response response = okHttpClient.newCall(request).execute();
        String result=response.body().string();
        parseToVacancyList(result);
    }

    private void parseToVacancyList(String result) {
        Type vacancyType=new TypeToken<List<Vacancy>>(){}.getType();

        List<Vacancy> vacancyList= null;
        try {
            vacancyList=gson.fromJson(result, vacancyType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(vacancyList==null)vacancyList= Collections.emptyList();
        EventBus.getDefault().post(new VacancyListEvent(vacancyList));
        VacancyDao vacancyDao=daoMaster.newSession().getVacancyDao();
        if(!vacancyList.isEmpty())
            vacancyDao.queryBuilder().where(VacancyDao.Properties.CategoryId.eq(vacancyList.get(0).getCategoryId()))
                    .buildDelete().forCurrentThread();
        vacancyDao.insertOrReplaceInTx(vacancyList);
    }

    @Override
    protected void onCancel() {

    }
}
