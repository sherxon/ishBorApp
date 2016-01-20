package uz.ishborApp.DAO;


import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.greenrobot.event.EventBus;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.Entity.Category;
import uz.ishborApp.Entity.CategoryDao;
import uz.ishborApp.Entity.DaoMaster;
import uz.ishborApp.Entity.Vacancy;
import uz.ishborApp.Entity.VacancyDao;

/**
 * Created by sherxon on 1/15/16.
 */
@Singleton
public class DbBalance {

    @Inject
    SQLiteOpenHelper db;

    @Inject
    OkHttpClient okHttpClient;

    @Inject
    Gson gson;

    @Inject
    DaoMaster daoMaster;

    @Inject
    public DbBalance() {}


    public void loadCategoryToLocalDb(){
        new AsyncTask<String,Integer, String>(){
            @Override
            protected String doInBackground(String... urls) {
                String result="";
                for (int i = 0; i <urls.length; i++) {
                    Request request= new Request.Builder().url(urls[i]).build();
                    try {
                        Response response = okHttpClient.newCall(request).execute();
                        result=response.body().string();
                        return result;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Type categoryType=new TypeToken<List<Category>>(){}.getType();

                List<Category> categoryList= Collections.emptyList();
                try {
                    categoryList=gson.fromJson(s, categoryType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                EventBus.getDefault().post(categoryList);
                CategoryDao categoryDao=daoMaster.newSession().getCategoryDao();
                categoryDao.deleteAll();
                categoryDao.insertOrReplaceInTx(categoryList);

            }
        }.execute(Globals.LOCAL_CATEGORY_URL);
    }

    public void checkCategoryUpdate(){
        CategoryDao.createTable(db.getWritableDatabase(), true);
        CategoryDao categoryDao=daoMaster.newSession().getCategoryDao();
        long count =  categoryDao.count();
        if(count==0) loadCategoryToLocalDb();
        else EventBus.getDefault().post(categoryDao.loadAll());

    }
    public void loadVacancyToLocalDb(Long categoryId){
        new AsyncTask<String,Integer, String>(){
            @Override
            protected String doInBackground(String... urls) {
                String result="";
                for (int i = 0; i <urls.length; i++) {
                    Request request= new Request.Builder().url(urls[i]).build();
                    try {
                        Response response = okHttpClient.newCall(request).execute();
                        return response.body().string();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Type vacancyType=new TypeToken<List<Vacancy>>(){}.getType();

                List<Vacancy> vacancyList= null;
                try {
                    vacancyList=gson.fromJson(s, vacancyType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(vacancyList==null)vacancyList=Collections.emptyList();
                EventBus.getDefault().post(vacancyList);
                VacancyDao vacancyDao=daoMaster.newSession().getVacancyDao();
                if(!vacancyList.isEmpty())
                vacancyDao.queryBuilder().where(VacancyDao.Properties.CategoryId.eq(vacancyList.get(0).getCategoryId()))
                        .buildDelete().forCurrentThread();
                vacancyDao.insertOrReplaceInTx(vacancyList);

            }
        }.execute(Globals.LOCAL_CATEGORY_URL+"/"+categoryId+"/vacancy");
    }

    public void checkVacancyUpdate(Long categoryId){
        VacancyDao.createTable(db.getWritableDatabase(), true);
        CategoryDao categoryDao=daoMaster.newSession().getCategoryDao();
        List<Vacancy> vacancyList=categoryDao.load(categoryId).getVacancyList();
        long count =vacancyList.size();
        if(count==0) loadVacancyToLocalDb(categoryId);
        else EventBus.getDefault().post(vacancyList);

    }

}
