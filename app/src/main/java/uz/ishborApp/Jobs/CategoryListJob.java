package uz.ishborApp.Jobs;

import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.Params;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


import de.greenrobot.event.EventBus;
import uz.ishborApp.AppComponent;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.Entity.Category;
import uz.ishborApp.Entity.CategoryDao;
import uz.ishborApp.Events.CategoryListEvent;

/**
 * Created by sherxon on 2/13/16.
 */
public class CategoryListJob extends BaseJob{


    public CategoryListJob() {
        super(new Params(900));
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
        CategoryDao.createTable(daoMaster.getDatabase(), true);
        CategoryDao categoryDao=daoMaster.newSession().getCategoryDao();
        if(categoryDao.count()==0)
            loadFromApi();
        else
            EventBus.getDefault().post(new CategoryListEvent(categoryDao.loadAll()));

    }

    private void loadFromApi() throws IOException {

        Request request= new Request.Builder().url(Globals.LOCAL_CATEGORY_URL).build();
        Response response = okHttpClient.newCall(request).execute();
        String result=response.body().string();
        parseToCategoryList(result);
    }

    private void parseToCategoryList(String result) {
        Type categoryType=new TypeToken<List<Category>>(){}.getType();

        List<Category> categoryList =gson.fromJson(result, categoryType);

        EventBus.getDefault().post(new CategoryListEvent(categoryList));

        CategoryDao categoryDao=daoMaster.newSession().getCategoryDao();
        categoryDao.deleteAll();
        categoryDao.insertOrReplaceInTx(categoryList);
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
