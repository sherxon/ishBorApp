package uz.ishborApp.Components;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.JobManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import uz.ishborApp.Entity.DaoMaster;
import uz.ishborApp.Entity.Search;

/**
 * Created by sherxon on 1/21/16.
 */
public class SearchController {

    @Inject
    DaoMaster daoMaster;

    @Inject
    OkHttpClient okHttpClient;

    @Inject
    Gson gson;



   private final static SearchController controller= new SearchController();

    @Inject @Singleton
    public SearchController() {
    }

    public static SearchController getInstance() {
        return controller;
    }


    public List<VacancySearchSuggestion> getSearchHistory(int count) {
        List<VacancySearchSuggestion> suggestionList= new ArrayList<>();
                for (long i = 0; i < count; i++) {
                    Search search=new Search(i, "Salom"+i, new Date());
                        suggestionList.add(new VacancySearchSuggestion(search, true));
                }
        return suggestionList;

        //List<Search> searchList=daoMaster.newSession().getSearchDao().
        //      queryBuilder().orderDesc(SearchDao.Properties.Created).
//                        limit(4).build().list();
    }


    public String searchTags(String query) throws IOException {
        Request request= new Request.Builder().url(Globals.LOCAL_SEARCH_URL+"/tag/"+ query).build();
        Response response = new OkHttpClient().newCall(request).execute();
        return response.body().string();
    }

    public List<VacancySearchSuggestion> searchSuggestionList(String json){
        Type searchType=new TypeToken<List<Search>>(){}.getType();
        List<Search> searchList=new Gson().fromJson(json, searchType);
        List<VacancySearchSuggestion> suggestionList=new LinkedList<>();
        for (Search search : searchList) {
            suggestionList.add(new VacancySearchSuggestion(search, false));
        }
        return suggestionList;
    }
}
