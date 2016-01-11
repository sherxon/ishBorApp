package uz.ishborApp.DAO;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.Entity.Category;
import uz.ishborApp.Entity.Vacancy;
import uz.ishborApp.VacancyCategoryActivity;

/**
 * Created by sherxon on 1/7/16.
 */

public class VacancyDao extends AsyncTask<String, Integer, String> {

    OkHttpClient httpClient= new OkHttpClient();

    @Override
    protected String doInBackground(String... urls) {
        String result="";
        for (int i = 0; i <urls.length; i++) {
            Request request= new Request.Builder().url(urls[i]).build();
            try {
                Response response = httpClient.newCall(request).execute();
                result=response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(result);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Gson gson= new Gson();
        Type vacancyType=new TypeToken<List<Vacancy>>(){}.getType();

        List<Vacancy> vacancyList= Collections.emptyList();
        try {
            vacancyList=gson.fromJson(s, vacancyType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventBus.getDefault().post(vacancyList);
    }

}
