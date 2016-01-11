package uz.ishborApp.Components;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URL;

/**
 * Created by sherxon on 1/10/16.
 */
public class BackgroundTask extends AsyncTask<String, Integer, String> {

    OkHttpClient httpClient= new OkHttpClient();

    @Override
    protected String doInBackground(String... urls) {
        String result="";
        for (int i = 0; i <urls.length; i++) {
            Request request= new Request.Builder().url(urls[i]).build();
            Response response= null;
            try {
                response = httpClient.newCall(request).execute();
                result=response.body().toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(result);
        }
        return result;
    }
}
